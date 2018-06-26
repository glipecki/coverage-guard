package net.lipecki.covgrd.coverageguard.classmonitor

import net.lipecki.covgrd.coverageguard.consumer.ConsumerStatus
import net.lipecki.covgrd.coverageguard.consumer.ConsumerTriggered
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/class-coverage-monitor")
class ClassCoverageMonitorRestController(val service: ClassCoverageMonitorService) {

    @GetMapping("/status")
    fun status(): ConsumerStatus = service.getConsumerStatus()

    @PostMapping("/trigger")
    fun trigger(): ConsumerTriggered = service.trigger()

    @GetMapping("/{project}/{branch}/classes")
    fun projectClasses(
            @PathVariable project: String,
            @PathVariable branch: String,
            @RequestParam pattern: String?): List<ClassCoverageSummary> = service.getProjectClasses(project, branch, pattern).map {
        ClassCoverageSummary(
                qualifiedName = it.classQualifiedName,
                methodCovered = it.methodsCovered,
                methodMissed = it.methodsMissed
        )
    }

}