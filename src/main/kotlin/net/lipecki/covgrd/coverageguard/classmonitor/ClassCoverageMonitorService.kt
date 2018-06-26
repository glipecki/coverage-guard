package net.lipecki.covgrd.coverageguard.classmonitor

import net.lipecki.covgrd.coverageguard.consumer.ConsumerService
import net.lipecki.covgrd.coverageguard.consumer.ConsumerStatus
import net.lipecki.covgrd.coverageguard.consumer.ConsumerTriggered
import net.lipecki.covgrd.coverageguard.report.ReportRepository
import java.util.concurrent.Executor

const val ClassCoverageMonitorName = "ClassCoverageMonitor"

class ClassCoverageMonitorService(private val reportRepository: ReportRepository, private val syncTaskExecutor: Executor) : ConsumerService {

    override fun getConsumerStatus(): ConsumerStatus = ConsumerStatus(
            handled = countHandled(),
            pending = countPending()
    )

    override fun trigger(): ConsumerTriggered = ConsumerTriggered(countPending())

    private fun countHandled() = countByConsumer(true)

    private fun countPending() = countByConsumer(false)

    private fun countByConsumer(exists: Boolean) = reportRepository.countByConsumerExists(ClassCoverageMonitorName, exists)

}