package net.lipecki.covgrd.coverageguard.classmonitor

import net.lipecki.covgrd.coverageguard.consumer.ConsumerRepository
import net.lipecki.covgrd.coverageguard.report.ClassCoverageReportRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor



@Configuration
class ClassCoverageMonitorConfiguration {

    @Bean
    fun classCoverageMonitorService(consumerRepository: ConsumerRepository, reportRepository: ClassCoverageReportRepository)
            = ClassCoverageMonitorService(consumerRepository, reportRepository, classCoverageMonitorSyncExecutor())

    @Bean
    fun classCoverageMonitorSyncExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 1
        executor.maxPoolSize = 1
        executor.setQueueCapacity(500)
        executor.setThreadNamePrefix("$ClassCoverageMonitorName-")
        executor.initialize()
        return executor
    }

}