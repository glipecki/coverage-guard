package net.lipecki.covgrd.coverageguard.classmonitor

import net.lipecki.covgrd.coverageguard.consumer.ConsumerRepository
import net.lipecki.covgrd.coverageguard.consumer.ConsumerRepositoryExtended
import net.lipecki.covgrd.coverageguard.report.ReportRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
class ClassCoverageMonitorConfiguration {

    @Bean
    fun classCoverageMonitorService(
            consumerRepository: ConsumerRepository,
            consumerRepositoryExtended: ConsumerRepositoryExtended,
            reportRepository: ReportRepository
    ) = ClassCoverageMonitorService(
            consumerRepository,
            consumerRepositoryExtended,
            reportRepository,
            classCoverageMonitorSyncExecutor()
    )

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