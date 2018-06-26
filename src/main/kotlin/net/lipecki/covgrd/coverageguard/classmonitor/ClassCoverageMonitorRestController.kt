package net.lipecki.covgrd.coverageguard.classmonitor

import net.lipecki.covgrd.coverageguard.consumer.ConsumerStatus
import net.lipecki.covgrd.coverageguard.consumer.ConsumerTriggered
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/class-coverage-monitor")
class ClassCoverageMonitorRestController(val service: ClassCoverageMonitorService) {

    @GetMapping("/status")
    fun status(): ConsumerStatus = service.getConsumerStatus()

    @PostMapping("/trigger")
    fun trigger(): ConsumerTriggered = service.trigger()

}