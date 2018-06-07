package net.lipecki.covgrd.coverageguard.consumer

import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.*

@Component
class ConsumerRepositoryCustomImpl(val mongoTemplate: MongoTemplate) : ConsumerRepositoryCustom {

    override fun findNotConsumedEntityIds(consumerType: String, refCollection: String, refField: String, limit: Long): Flux<String> {
        val entityToConsumerAggregate = Aggregation.newAggregation(
                AggregationOperation(function = {
                    lookupJoiningRefCollectionWithConsumers(consumerType, refField)
                }),
                Aggregation.match(Criteria.where("consumers").size(0)),
                Aggregation.group("\$$refField"),
                Aggregation.limit(limit)
        )
        val aggregate = mongoTemplate.aggregate(
                entityToConsumerAggregate,
                refCollection,
                Document::class.java
        )
        return Flux.fromIterable(aggregate.mappedResults.map { it["_id"] as String })
    }


    private fun lookupJoiningRefCollectionWithConsumers(consumerType: String, refField: String): Document {
        return Document(
                "\$lookup",
                Document()
                        .append("from", "consumers")
                        .append("let", Document().append("refId", "\$$refField"))
                        .append("pipeline", Arrays.asList(
                                Document().append("\$match", Document().append("consumer", consumerType)),
                                Document().append("\$match", Document().append("\$expr", Document().append("\$eq", Arrays.asList("\$refId", "\$\$refId"))))
                        ))
                        .append("as", "consumers")
        )
    }

}