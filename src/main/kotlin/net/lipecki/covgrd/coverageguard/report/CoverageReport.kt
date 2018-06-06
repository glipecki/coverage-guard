package net.lipecki.covgrd.coverageguard.report

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "reports")
class CoverageReport(
        @Id var id: String? = null,
        val project: String,
        val branch: String,
        val reportUuid: String,
        val reportDate: Date,
        val classCoverage: ClassCoverage
)