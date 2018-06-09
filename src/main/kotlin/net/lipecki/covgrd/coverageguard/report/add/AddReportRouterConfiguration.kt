package net.lipecki.covgrd.coverageguard.report.add

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class AddReportRouterConfiguration {

    @Bean
    fun addReportRoute(reportImportHandler: AddReportHandler) = router {
        ("/report").nest {
            POST("/add", reportImportHandler::importReport)
        }
    }

}