package net.lipecki.covgrd.coverageguard.consumer

import reactor.core.publisher.Flux

interface ConsumerRepositoryCustom {

    fun findNotConsumedEntityIds(
            consumerType: String,
            refCollection: String,
            refField: String,
            limit: Long = 1
    ) : Flux<String>

}