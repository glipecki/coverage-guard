package net.lipecki.covgrd.coverageguard.consumer

import org.springframework.data.mongodb.repository.MongoRepository
import reactor.core.publisher.Mono

interface ConsumerRepository : MongoRepository<ConsumerEntry, String> {

    fun countByConsumerTypeAndRefCollection(type: String, refCollection: String): Mono<Long>

}