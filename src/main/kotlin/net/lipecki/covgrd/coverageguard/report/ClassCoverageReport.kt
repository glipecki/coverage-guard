package net.lipecki.covgrd.coverageguard.report

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

const val ClassCoverageReportCollection = "classCoverageReports"

@Document(collection = ClassCoverageReportCollection)
class ClassCoverageReport(
        @Id var id: String? = null,
        val project: String,
        val branch: String,
        val reportUuid: String,
        val reportDate: Date,
        val classCoverage: ClassCoverage
)