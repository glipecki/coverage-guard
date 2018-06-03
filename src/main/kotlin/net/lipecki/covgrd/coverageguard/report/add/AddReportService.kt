package net.lipecki.covgrd.coverageguard.report.add

import net.lipecki.covgrd.coverageguard.report.ClassCoverage
import net.lipecki.covgrd.coverageguard.report.ClassCoverageDocument
import net.lipecki.covgrd.coverageguard.report.ReportRepository
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class AddReportService(val reportParserFactory: ReportParserFactory, val reportRepository: ReportRepository) {

    fun addReport(project: String, content: Flux<DataBuffer>, format: String): Mono<String> {
        val reportUuid = UUID.randomUUID().toString()
        val reportDate = Date()
        return reportRepository
                .saveAll(
                        reportParserFactory
                                .getParser(format)
                                .parse(content)
                                .map { asDocument(project, reportUuid, reportDate, it) }
                )
                .last()
                .map { reportUuid }
    }

    private fun asDocument(project: String, reportUuid: String, reportDate: Date, it: ClassCoverage): ClassCoverageDocument = ClassCoverageDocument(
            project = project,
            reportUuid = reportUuid,
            reportDate = reportDate,
            classCoverage = it
    )

}