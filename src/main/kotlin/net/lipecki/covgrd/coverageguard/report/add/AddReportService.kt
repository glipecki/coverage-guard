package net.lipecki.covgrd.coverageguard.report.add

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import net.lipecki.covgrd.coverageguard.coverage.ClassCoverageReport
import net.lipecki.covgrd.coverageguard.coverage.ClassCoverageReportRepository
import net.lipecki.covgrd.coverageguard.report.Report
import net.lipecki.covgrd.coverageguard.report.ReportRepository
import net.lipecki.covgrd.coverageguard.report.ReportState
import org.springframework.stereotype.Component
import java.io.InputStream
import java.util.*
import java.util.stream.Collectors.toList

@Component
class AddReportService(
        val reportParserFactory: ReportParserFactory,
        val classCoverageReportRepository: ClassCoverageReportRepository,
        val reportRepository: ReportRepository) {

    fun addReport(project: String, branch: String, format: String, content: InputStream): String {
        val report = reportRepository.save(Report(
                project = project,
                branch = branch,
                uuid = UUID.randomUUID().toString(),
                reportDate = Date(),
                state = ReportState.PENDING
        ))
        classCoverageReportRepository.saveAll(
                reportParserFactory
                        .getParser(format)
                        .parse(content)
                        .map { asDocument(report.uuid, it) }
                        .collect(toList<ClassCoverageReport>())
        )
        reportRepository.save(report.withState(ReportState.DONE))
        return report.uuid
    }

    private fun asDocument(reportUuid: String, classCoverage: ClassCoverage): ClassCoverageReport = ClassCoverageReport(
            reportUuid = reportUuid,
            classCoverage = classCoverage
    )

}