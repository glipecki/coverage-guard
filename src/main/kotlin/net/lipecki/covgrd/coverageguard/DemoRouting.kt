package net.lipecki.covgrd.coverageguard

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class DemoRouting {

    @Bean
    fun demoRouter(demoHandler: DemoHandler) = router {
        ("/demo").nest {
            GET("/", demoHandler::getHello)
        }
    }

}