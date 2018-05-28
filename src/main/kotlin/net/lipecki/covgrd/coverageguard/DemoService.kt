package net.lipecki.covgrd.coverageguard

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class DemoService {

    fun getHello() = Mono.just("Hello...")

}