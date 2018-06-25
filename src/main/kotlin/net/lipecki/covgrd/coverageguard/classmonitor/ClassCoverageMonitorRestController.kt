package net.lipecki.covgrd.coverageguard.classmonitor

import net.lipecki.covgrd.coverageguard.consumer.ConsumerStatus
import net.lipecki.covgrd.coverageguard.consumer.ConsumerTriggered
import net.lipecki.covgrd.coverageguard.logger
import org.omg.CORBA.ServerRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/class-coverage-monitor")
class ClassCoverageMonitorRestController(val service: ClassCoverageMonitorService) {

    private val log by logger()

    @GetMapping("/status")
    fun status(request: ServerRequest): ConsumerStatus = service.getConsumerStatus()

    @PostMapping("/trigger")
    fun trigger(request: ServerRequest): ConsumerTriggered = service.trigger()

}