package net.lipecki.covgrd.coverageguard.report

import org.springframework.data.mongodb.repository.MongoRepository

interface ReportRepository : MongoRepository<Report, String>