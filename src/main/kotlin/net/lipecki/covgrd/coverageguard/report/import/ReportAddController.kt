package net.lipecki.covgrd.coverageguard.report.import

import net.lipecki.covgrd.coverageguard.logger
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class ImportRequest(val file: FilePart, val format: String)

@RestController
@RequestMapping("/report")
class ImportReportController(val reportParserFactory: ReportFormatParserFactory, val importReportCommand: ImportReportCommand) {

    val log by logger()

    @PostMapping
    fun addReport(importRequest: ImportRequest) {
        log.info("Request to import Coverage report [import={}]", importRequest)
        val parser = reportParserFactory.getParser(importRequest.format)
        val parsedReport = parser.parse(importRequest.file)
        importReportCommand.import(parsedReport)
        // add report with parser based on report format
    }

}