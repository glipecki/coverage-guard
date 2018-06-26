package net.lipecki.covgrd.coverageguard.report

import org.springframework.data.mongodb.repository.CountQuery
import org.springframework.data.mongodb.repository.MongoRepository

interface ReportRepository : MongoRepository<Report, String> {

    fun findByConsumersNotContaining(consumerName: String): List<Report>

    @CountQuery("{?#{'consumers.' + #consumerName}: { \$exists: false }}")
    fun countByConsumersNotContaining(consumerName: String): Long

}