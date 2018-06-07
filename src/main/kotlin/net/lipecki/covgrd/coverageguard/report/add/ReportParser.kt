package net.lipecki.covgrd.coverageguard.report.add

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import org.springframework.core.io.buffer.DataBuffer
import reactor.core.publisher.Flux

interface ReportParser {

    fun getFormatName(): String
    fun parse(content: Flux<DataBuffer>): Flux<ClassCoverage>

}