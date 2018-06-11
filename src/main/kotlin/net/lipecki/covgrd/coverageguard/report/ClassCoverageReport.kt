package net.lipecki.covgrd.coverageguard.report

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

const val ClassCoverageReportCollection = "classCoverageReports"

@Document(collection = ClassCoverageReportCollection)
data class ClassCoverageReport(
        @Id var id: String? = null,
        @Indexed val reportUuid: String,
        val classCoverage: ClassCoverage
)