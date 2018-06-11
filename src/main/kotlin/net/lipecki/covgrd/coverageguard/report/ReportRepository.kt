package net.lipecki.covgrd.coverageguard.report

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ReportRepository : ReactiveMongoRepository<Report, String>