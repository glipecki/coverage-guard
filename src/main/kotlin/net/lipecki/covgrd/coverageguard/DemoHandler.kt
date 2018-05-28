package net.lipecki.covgrd.coverageguard

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class DemoHandler(val service: DemoService) {

    fun getHello(request: ServerRequest) = ServerResponse.ok().body(service.getHello(), String::class.java)

}