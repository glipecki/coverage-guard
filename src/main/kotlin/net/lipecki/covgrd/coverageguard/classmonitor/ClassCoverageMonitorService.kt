package net.lipecki.covgrd.coverageguard.classmonitor

import net.lipecki.covgrd.coverageguard.consumer.*
import net.lipecki.covgrd.coverageguard.logger
import net.lipecki.covgrd.coverageguard.report.ClassCoverageReportCollection
import net.lipecki.covgrd.coverageguard.report.ReportCollection
import net.lipecki.covgrd.coverageguard.report.ReportRepository
import reactor.core.publisher.Mono
import java.util.concurrent.Executor

const val ClassCoverageMonitorName = "ClassCoverageMonitor"

class ClassCoverageMonitorService(
        private val consumerRepository: ConsumerRepository,
        private val consumerRepositoryExtended: ConsumerRepositoryExtended,
        private val reportRepository: ReportRepository,
        private val syncTaskExecutor: Executor
) : ConsumerService {

    private val log by logger()

    override fun getConsumerStatus(): Mono<ConsumerStatus> = Mono
            .zip(
                    consumerRepository.countByConsumerTypeAndRefCollection(ClassCoverageMonitorName, ClassCoverageReportCollection),
                    reportRepository.count()
            )
            .map { ConsumerStatus("up", it.t1, it.t2) }
            .doOnNext { log.info("Consumer status [consumer={}, status={}]", ClassCoverageMonitorName, it) }

    override fun trigger(): Mono<ConsumerTriggered> = consumerRepositoryExtended
            .countNotConsumedEntityIds(ClassCoverageMonitorName, ReportCollection, "reportUuid", null)
            .doOnNext { scheduleSynchronization() }
            .map { ConsumerTriggered(it) }

    private fun scheduleSynchronization() {
        syncTaskExecutor.execute {
            consumerRepositoryExtended.findNotConsumedEntityIds(ClassCoverageMonitorName, ReportCollection, "uuid", 5)
                    .doOnNext { log.debug("Class coverage report to process [reportUuid={}]", it) }
                    .flatMap { consumerRepositoryExtended.markAsDone(ClassCoverageMonitorName, ReportCollection, it) }
                    .last()
//                    .map { reportRepository.findByReportUuid(it) }
                    .subscribe {
//                        it.subscribe {
//                            log.debug("ClassCoverageReport: {}", it)
//                        }
                        log.debug("Result: {}", it)
                    }
//                    .map { reportRepository.findByReportUuid(it) }
//                    .subscribe {
//                        it.subscribe { }
//                    }
        }
    }

}