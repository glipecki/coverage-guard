package net.lipecki.covgrd.coverageguard.consumer

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
interface ConsumerRepository : ReactiveMongoRepository<ConsumerEntry, String>, ConsumerRepositoryCustom {

    fun countByTypeAndRefType(type: String, refType: String): Mono<Long>

}