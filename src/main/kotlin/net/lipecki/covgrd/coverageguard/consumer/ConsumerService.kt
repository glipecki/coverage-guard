package net.lipecki.covgrd.coverageguard.consumer

import reactor.core.publisher.Mono


interface ConsumerService {

    fun getConsumerStatus(): Mono<ConsumerStatus>

    fun trigger(): Mono<ConsumerTriggered>

}