package net.lipecki.covgrd.coverageguard.report.import.jacoco

import net.lipecki.covgrd.coverageguard.report.import.ReportFormatParser
import org.springframework.stereotype.Component

@Component
class JacocoCsvReportParser : ReportFormatParser {

    override fun getFormatName(): String = "jacoco/csv"

}