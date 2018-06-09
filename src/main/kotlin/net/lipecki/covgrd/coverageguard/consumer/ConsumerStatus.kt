package net.lipecki.covgrd.coverageguard.consumer


data class ConsumerStatus(
        val status: String,
        val handled: Long,
        val all: Long
)