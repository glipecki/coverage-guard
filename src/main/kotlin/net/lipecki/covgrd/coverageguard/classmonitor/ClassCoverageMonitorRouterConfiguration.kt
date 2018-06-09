package net.lipecki.covgrd.coverageguard.classmonitor

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class ClassCoverageMonitorRouterConfiguration {

    @Bean
    fun classMonitorRouting(handler: ClassCoverageMonitorHandler) = router {
        ("/class-coverage-monitor").nest {
            GET("/status", handler::status)
            POST("/trigger", handler::trigger)
        }
    }

}