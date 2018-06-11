package net.lipecki.covgrd.coverageguard.report.add

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import net.lipecki.covgrd.coverageguard.report.*
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class AddReportService(
        val reportParserFactory: ReportParserFactory,
        val classCoverageReportRepository: ClassCoverageReportRepository,
        val reportRepository: ReportRepository) {

    fun addReport(project: String, branch: String, content: Flux<DataBuffer>, format: String): Mono<String> =
            reportRepository
                    .save(Report(
                            project = project,
                            branch = branch,
                            uuid = UUID.randomUUID().toString(),
                            reportDate = Date(),
                            state = ReportState.IN_PROGRESS
                    ))
                    .flatMap { report ->
                        classCoverageReportRepository
                                .saveAll(
                                        reportParserFactory
                                                .getParser(format)
                                                .parse(content)
                                                .onBackpressureBuffer()
                                                .map { asDocument(report.uuid, it) }
                                )
                                .last()
                                .map { report }
                    }
                    .flatMap {
                        reportRepository.findById(it.id ?: throw RuntimeException("Report from DB without ID!"))
                    }
                    .map { it.withState(ReportState.DONE) }
                    .flatMap { reportRepository.save(it) }
                    .map { it.uuid }

    private fun asDocument(reportUuid: String, classCoverage: ClassCoverage): ClassCoverageReport = ClassCoverageReport(
            reportUuid = reportUuid,
            classCoverage = classCoverage
    )

}