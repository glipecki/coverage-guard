package net.lipecki.covgrd.coverageguard.classmonitor

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Component

@Component
interface ClassCoverageMonitorRepository : MongoRepository<ClassCoverageMonitorStatus, String> {

    fun deleteByProjectQualifiedName(qualifiedName: String)

    fun findByProjectQualifiedName(projectQualifiedName: String): List<ClassCoverageMonitorStatus>

    @Query("{'classQualifiedName': { \$regex: ?0, \$options: 'i' } }")
    fun findByProjectQualifiedNamePattern(pattern: String): List<ClassCoverageMonitorStatus>

}