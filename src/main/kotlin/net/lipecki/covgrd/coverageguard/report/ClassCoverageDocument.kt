package net.lipecki.covgrd.coverageguard.report

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "classCoverages")
class ClassCoverageDocument(
        @Id var id: String? = null,
        val project: String,
        val reportUuid: String,
        val reportDate: Date,
        val classCoverage: ClassCoverage
)