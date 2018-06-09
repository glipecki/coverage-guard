package net.lipecki.covgrd.coverageguard.classmonitor

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ClassCoverageMonitorHandler(val service: ClassCoverageMonitorService) {

    fun status(request: ServerRequest): Mono<ServerResponse> = service.getConsumerStatus().flatMap { okResponseFromObject(it) }

    fun trigger(request: ServerRequest): Mono<ServerResponse> = service.trigger().flatMap { okResponseFromObject(it) }

    private fun okResponseFromObject(it: Any) = ServerResponse.ok().body(BodyInserters.fromObject(it))

}