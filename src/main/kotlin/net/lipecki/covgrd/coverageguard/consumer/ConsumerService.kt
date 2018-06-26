package net.lipecki.covgrd.coverageguard.consumer


interface ConsumerService {

    fun getConsumerStatus(): ConsumerStatus

    fun trigger(): ConsumerTriggered

}