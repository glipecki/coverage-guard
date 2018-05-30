package net.lipecki.covgrd.coverageguard.report.add

import net.lipecki.covgrd.coverageguard.report.Report
import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Mono

interface ReportFormatParser {

    fun getFormatName(): String
    fun parse(file: FilePart): Mono<Report>

}