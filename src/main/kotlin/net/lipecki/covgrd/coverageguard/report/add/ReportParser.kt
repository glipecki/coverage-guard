package net.lipecki.covgrd.coverageguard.report.add

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import java.io.InputStream
import java.util.stream.Stream

interface ReportParser {

    fun getFormatName(): String
    fun parse(content: InputStream): Stream<ClassCoverage>

}