package net.lipecki.covgrd.coverageguard.classmonitor

import net.lipecki.covgrd.coverageguard.consumer.ConsumerRepository
import net.lipecki.covgrd.coverageguard.consumer.ConsumerService
import net.lipecki.covgrd.coverageguard.consumer.ConsumerStatus
import net.lipecki.covgrd.coverageguard.consumer.ConsumerTriggered
import net.lipecki.covgrd.coverageguard.logger
import net.lipecki.covgrd.coverageguard.report.ClassCoverageReportCollection
import net.lipecki.covgrd.coverageguard.report.ClassCoverageReportRepository
import reactor.core.publisher.Mono
import java.util.concurrent.Executor

const val ClassCoverageMonitorName = "ClassCoverageMonitor"

class ClassCoverageMonitorService(
        private val consumerRepository: ConsumerRepository,
        private val reportRepository: ClassCoverageReportRepository,
        private val syncTaskExecutor: Executor
) : ConsumerService {

    private val log by logger()

    override fun getConsumerStatus(): Mono<ConsumerStatus> = Mono
            .zip(
                    consumerRepository.countByTypeAndRefType(ClassCoverageMonitorName, ClassCoverageReportCollection),
                    reportRepository.count()
            )
            .map { ConsumerStatus("up", it.t1, it.t2) }
            .doOnNext { log.info("Consumer status [consumer={}, status={}]", ClassCoverageMonitorName, it) }

    override fun trigger(): Mono<ConsumerTriggered> = consumerRepository
            .countNotConsumedEntityIds(ClassCoverageMonitorName, ClassCoverageReportCollection, "reportUuid", null)
            .doOnNext { scheduleSynchronization() }
            .map { ConsumerTriggered(it) }

    private fun scheduleSynchronization() {
        syncTaskExecutor.execute {
            log.info("\n\n\n\n\n\n\ntask scheduled\n\n\n\n\n\n")
            consumerRepository.findNotConsumedEntityIds(ClassCoverageMonitorName, ClassCoverageReportCollection, "reportUuid", 100)
                    .doOnNext { log.debug("Class coverage report to process [reportUuid={}]", it) }
                    .map { reportRepository.findByReportUuid(it) }
                    .subscribe {
                        it.subscribe { }
                    }
        }
    }

}