package net.lipecki.covgrd.coverageguard.report

import org.springframework.data.mongodb.repository.CountQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ReportRepository : MongoRepository<Report, String> {

    @Query("{?#{'consumers.' + #consumerName}: { \$exists: ?#{#exists} }}")
    fun findByConsumerExists(consumerName: String, exists: Boolean): List<Report>

    @CountQuery("{?#{'consumers.' + #consumerName}: { \$exists: ?#{#exists} }}")
    fun countByConsumerExists(consumerName: String, exists: Boolean): Long

}