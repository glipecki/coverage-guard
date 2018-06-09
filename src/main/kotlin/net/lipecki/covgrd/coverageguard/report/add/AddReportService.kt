package net.lipecki.covgrd.coverageguard.report.add

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import net.lipecki.covgrd.coverageguard.report.ClassCoverageReport
import net.lipecki.covgrd.coverageguard.report.ClassCoverageReportRepository
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class AddReportService(val reportParserFactory: ReportParserFactory, val reportRepository: ClassCoverageReportRepository) {

    fun addReport(project: String, branch: String, content: Flux<DataBuffer>, format: String): Mono<String> {
        val reportUuid = UUID.randomUUID().toString()
        val reportDate = Date()
        return reportRepository
                .saveAll(
                        reportParserFactory
                                .getParser(format)
                                .parse(content)
                                .onBackpressureBuffer()
                                .map { asDocument(project, branch, reportUuid, reportDate, it) }
                )
                .last()
                .map { reportUuid }
    }

    private fun asDocument(project: String, branch: String, reportUuid: String, reportDate: Date, it: ClassCoverage): ClassCoverageReport = ClassCoverageReport(
            project = project,
            branch = branch,
            reportUuid = reportUuid,
            reportDate = reportDate,
            classCoverage = it
    )

}