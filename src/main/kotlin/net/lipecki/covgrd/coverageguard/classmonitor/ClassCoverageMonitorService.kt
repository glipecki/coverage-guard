package net.lipecki.covgrd.coverageguard.classmonitor

import net.lipecki.covgrd.coverageguard.consumer.ConsumerService
import net.lipecki.covgrd.coverageguard.consumer.ConsumerStatus
import net.lipecki.covgrd.coverageguard.consumer.ConsumerTriggered
import net.lipecki.covgrd.coverageguard.report.ReportRepository
import java.util.concurrent.Executor

const val ClassCoverageMonitorName = "ClassCoverageMonitor"

class ClassCoverageMonitorService(private val reportRepository: ReportRepository, private val syncTaskExecutor: Executor) : ConsumerService {

    override fun getConsumerStatus(): ConsumerStatus = ConsumerStatus(
            handled = countByConsumer(true),
            pending = countByConsumer(false)
    )

    override fun trigger(): ConsumerTriggered = ConsumerTriggered(-1)

    private fun countByConsumer(exists: Boolean) = reportRepository.countByConsumerExists(ClassCoverageMonitorName, exists)

}