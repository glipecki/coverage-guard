package net.lipecki.covgrd.coverageguard.report

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
interface ClassCoverageReportRepository : ReactiveMongoRepository<ClassCoverageReport, String> {

    fun findByReportUuid(reportUuid: String) : Flux<ClassCoverageReport>

}