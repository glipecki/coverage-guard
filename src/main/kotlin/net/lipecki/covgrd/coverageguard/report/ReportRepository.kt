package net.lipecki.covgrd.coverageguard.report

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Component

@Component
interface ReportRepository : ReactiveMongoRepository<ClassCoverageDocument, String>