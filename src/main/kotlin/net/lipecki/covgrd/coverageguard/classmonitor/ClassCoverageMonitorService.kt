package net.lipecki.covgrd.coverageguard.classmonitor

import net.lipecki.covgrd.coverageguard.consumer.BaseConsumerState
import net.lipecki.covgrd.coverageguard.consumer.ConsumerService
import net.lipecki.covgrd.coverageguard.consumer.ConsumerStatus
import net.lipecki.covgrd.coverageguard.consumer.ConsumerTriggered
import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import net.lipecki.covgrd.coverageguard.coverage.ClassCoverageReportRepository
import net.lipecki.covgrd.coverageguard.logger
import net.lipecki.covgrd.coverageguard.report.Report
import net.lipecki.covgrd.coverageguard.report.ReportRepository
import java.util.*
import java.util.concurrent.Executor
import java.util.stream.Collectors.toList

const val ClassCoverageMonitorName = "ClassCoverageMonitor"

class ClassCoverageMonitorService(
        private val reportRepository: ReportRepository,
        private val coverageRepository: ClassCoverageReportRepository,
        private val monitorRepository: ClassCoverageMonitorRepository,
        private val syncTaskExecutor: Executor
) : ConsumerService {

    private val log by logger()

    fun getProjectClasses(project: String, branch: String, pattern: String?): List<ClassCoverageMonitorStatus> {
        return if (pattern != null) {
            monitorRepository.findByProjectQualifiedNamePattern("^$project:$branch:$pattern$")
        } else {
            monitorRepository.findByProjectQualifiedName("$project:$branch")
        }
    }

    override fun getConsumerStatus(): ConsumerStatus = ConsumerStatus(
            handled = countHandled(),
            pending = countPending()
    )

    override fun trigger(): ConsumerTriggered {
        syncTaskExecutor.execute {
            val reportDate = Date()
            val reports = reportRepository.findByConsumerExists(ClassCoverageMonitorName, false).iterator()
            while (reports.hasNext()) {
                val report = reports.next()
                if (!reports.hasNext()) {
                    val classStatuses = coverageRepository.findByReportUuid(report.uuid)
                            .stream()
                            .map { it.classCoverage }
                            .map { asClassStatus(report, reportDate, it) }
                            .collect(toList())
                    log.info("Update class coverage monitor status [project={}, classes={}]", report.projectQualifiedName, classStatuses.size)
                    monitorRepository.deleteByProjectQualifiedName(report.projectQualifiedName)
                    monitorRepository.saveAll(classStatuses)

                }
                report.consumers[ClassCoverageMonitorName] = BaseConsumerState(
                        state = "DONE",
                        date = Date()
                )
                reportRepository.save(report)
            }
        }
        return ConsumerTriggered(countPending())
    }

    private fun asClassStatus(report: Report, reportDate: Date, classCoverage: ClassCoverage): ClassCoverageMonitorStatus {
        return ClassCoverageMonitorStatus(
                report = report.uuid,
                project = report.project,
                branch = report.branch,
                groupName = classCoverage.groupName,
                packageName = classCoverage.packageName,
                className = classCoverage.className,
                date = reportDate,
                methodsCovered = classCoverage.coverage.methodStat.covered,
                methodsMissed = classCoverage.coverage.methodStat.missed
        )
    }

    private fun countHandled() = countByConsumer(true)

    private fun countPending() = countByConsumer(false)

    private fun countByConsumer(exists: Boolean) = reportRepository.countByConsumerExists(ClassCoverageMonitorName, exists)

}