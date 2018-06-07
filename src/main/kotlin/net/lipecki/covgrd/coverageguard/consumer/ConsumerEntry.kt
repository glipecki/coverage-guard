package net.lipecki.covgrd.coverageguard.consumer

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "consumers")
data class ConsumerEntry(
        @Id var id: String? = null,
        val type: String,
        val refType: String,
        val refId: String,
        val state: String
)