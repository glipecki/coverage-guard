package net.lipecki.covgrd.coverageguard.consumer


data class ConsumerStatus(
        val handled: Long,
        val pending: Long
)