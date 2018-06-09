package net.lipecki.covgrd.coverageguard.consumer

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ConsumerRepositoryCustom {

    fun findNotConsumedEntityIds(
            consumerType: String,
            refCollection: String,
            refField: String,
            limit: Long?
    ) : Flux<String>

    fun countNotConsumedEntityIds(
            consumerType: String,
            refCollection: String,
            refField: String,
            limit: Long?
    ) : Mono<Long>

}