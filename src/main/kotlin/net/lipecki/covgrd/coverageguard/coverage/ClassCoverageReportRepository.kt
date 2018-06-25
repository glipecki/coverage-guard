package net.lipecki.covgrd.coverageguard.coverage

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component

@Component
interface ClassCoverageReportRepository : MongoRepository<ClassCoverageReport, String> {

    fun findByReportUuid(reportUuid: String): List<ClassCoverageReport>

}