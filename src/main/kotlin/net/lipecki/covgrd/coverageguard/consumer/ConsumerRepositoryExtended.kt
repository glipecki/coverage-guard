package net.lipecki.covgrd.coverageguard.consumer

import net.lipecki.covgrd.coverageguard.logger
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import kotlin.collections.ArrayList

@Component
class ConsumerRepositoryExtended(val mongoTemplate: MongoTemplate, val consumerRepository: ConsumerRepository) {

    private val log by logger()

    fun findNotConsumedEntityIds(consumerType: String, refCollection: String, refField: String, limit: Long?): List<String> {
        val operations = getPendingAggregateOperations(consumerType, refCollection, refField)
        limit?.let { operations.add(Aggregation.limit(it)) }

        val aggregate = mongoTemplate.aggregate(
                Aggregation.newAggregation(operations),
                refCollection,
                Document::class.java
        )
        val result = aggregate
                .mappedResults
                .map { it["uuid"] }
                .filter { it is String }
                .map { it as String }
        return Flux.fromIterable(
                result
        )
    }

    fun countNotConsumedEntityIds(consumerType: String, refCollection: String, refField: String, limit: Long?): Long {
        log.debug(
                "Looking for not consumed entities [consumerType: {}, refCollection: {}, refField: {}, limit: {}]",
                consumerType,
                refCollection,
                refField,
                limit
        )

        val operations = getPendingAggregateOperations(consumerType, refCollection, refField)
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
                        .getOrElse(0) { _ -> 0 }
        )
    }

    fun markAsDone(consumerType: String, refCollection: String, refId: String): ConsumerEntry = consumerRepository.save(
            ConsumerEntry(
                    consumerType = consumerType,
                    refCollection = refCollection,
                    refId = refId,
                    state = "DONE"
            )
    )

    private fun getPendingAggregateOperations(consumerType: String, refCollection: String, refField: String): List<AggregationOperation> {
        val operations = ArrayList<AggregationOperation>()

        operations.add(AggregationOperation(function = {
            lookupJoiningRefCollectionWithConsumers(consumerType, refCollection, refField)
        }))
        operations.add(Aggregation.match(Criteria.where("consumers").size(0)))

        return operations
    }


    private fun lookupJoiningRefCollectionWithConsumers(consumerType: String, refCollection: String, refField: String): Document {
        return Document(
                "\$lookup",
                Document()
                        .append("from", "consumers")
                        .append("let", Document().append("refId", "\$$refField"))
                        .append("pipeline", Arrays.asList(
                                Document().append("\$match", Document().append("consumerType", consumerType)),
                                Document().append("\$match", Document().append("refCollection", refCollection)),
                                Document().append("\$match", Document().append("\$expr", Document().append("\$eq", Arrays.asList("\$refId", "\$\$refId"))))
                        ))
                        .append("as", "consumers")
        )
    }


}