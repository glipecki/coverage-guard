package net.lipecki.covgrd.coverageguard.consumer

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface ConsumerRepository : ReactiveMongoRepository<ConsumerEntry, String> {

    fun countByConsumerTypeAndRefCollection(type: String, refCollection: String): Mono<Long>

}