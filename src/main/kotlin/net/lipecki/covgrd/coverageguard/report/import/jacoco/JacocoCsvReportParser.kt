package net.lipecki.covgrd.coverageguard.report.import.jacoco

import net.lipecki.covgrd.coverageguard.report.import.Report
import net.lipecki.covgrd.coverageguard.report.import.ReportFormatParser
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component

@Component
class JacocoCsvReportParser : ReportFormatParser {

    override fun getFormatName(): String = "jacoco/csv"

    override fun parse(file: FilePart): Report {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}