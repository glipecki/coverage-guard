package net.lipecki.covgrd.coverageguard.consumer

import net.lipecki.covgrd.coverageguard.logger
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import kotlin.collections.ArrayList

@Component
class ConsumerRepositoryCustomImpl(val mongoTemplate: MongoTemplate) : ConsumerRepositoryCustom {

    private val log by logger()

    override fun findNotConsumedEntityIds(consumerType: String, refCollection: String, refField: String, limit: Long?): Flux<String> {
        val operations = getPendingAggregateOperations(consumerType, refField)
        limit?.let { operations.add(Aggregation.limit(it)) }

        val aggregate = mongoTemplate.aggregate(
                Aggregation.newAggregation(operations),
                refCollection,
                Document::class.java
        )
        return Flux.fromIterable(
                aggregate
                        .mappedResults
                        .map { it["_id"] }
                        .filter { it is String }
                        .map { it as String }
        )
    }

    override fun countNotConsumedEntityIds(consumerType: String, refCollection: String, refField: String, limit: Long?): Mono<Long> {
        log.debug(
                "Looking for not consumed entities [consumerType: {}, refCollection: {}, refField: {}, limit: {}]",
                consumerType,
                refCollection,
                refField,
                limit
        )

        val operations = getPendingAggregateOperations(consumerType, refField)
        operations.add(Aggregation.count().`as`("count"))

        val aggregate = mongoTemplate.aggregate(
                Aggregation.newAggregation(operations),
                refCollection,
                Document::class.java
        )
        return Mono.just(
                aggregate.mappedResults
                        .map { it["count"] }
                        .filter { it is Int }
                        .map { it as Int }
                        .map { it.toLong() }
                        .getOrElse(0, { _ -> 0 })
        )
    }

    private fun getPendingAggregateOperations(consumerType: String, refField: String): ArrayList<AggregationOperation> {
        val operations = ArrayList<AggregationOperation>()

        operations.add(Aggregation.group(refField))
        operations.add(AggregationOperation(function = {
            lookupJoiningRefCollectionWithConsumers(consumerType, refField)
        }))
        operations.add(Aggregation.match(Criteria.where("consumers").size(0)))

        return operations
    }


    private fun lookupJoiningRefCollectionWithConsumers(consumerType: String, refField: String): Document {
        return Document(
                "\$lookup",
                Document()
                        .append("from", "consumers")
                        .append("let", Document().append("refId", "\$$refField"))
                        .append("pipeline", Arrays.asList(
                                Document().append("\$match", Document().append("type", consumerType)),
                                Document().append("\$match", Document().append("\$expr", Document().append("\$eq", Arrays.asList("\$refId", "\$\$refId"))))
                        ))
                        .append("as", "consumers")
        )
    }


}