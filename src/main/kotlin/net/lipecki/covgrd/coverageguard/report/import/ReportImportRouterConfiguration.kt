package net.lipecki.covgrd.coverageguard.report.import

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class ReportImportRouterConfiguration {

    @Bean
    fun reportImportRouter(reportImportHandler: ReportImportHandler) = router {
        ("/report/import").nest {
            POST("", reportImportHandler::importReport)
        }
    }

}