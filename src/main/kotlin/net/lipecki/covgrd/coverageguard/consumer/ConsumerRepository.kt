package net.lipecki.covgrd.coverageguard.consumer

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Component

@Component
interface ConsumerRepository : ReactiveMongoRepository<ConsumerEntry, String>, ConsumerRepositoryCustom