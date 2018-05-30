package net.lipecki.covgrd.coverageguard.report.add

import net.lipecki.covgrd.coverageguard.logger
import net.lipecki.covgrd.coverageguard.report.ReportRepository
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.codec.multipart.FormFieldPart
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

private data class ImportReportRequest(val file: FilePart, val format: String)

@Component
class ReportImportHandler(val reportFormatParserFactory: ReportFormatParserFactory, val reportRepository: ReportRepository) {

    val log by logger()

    fun importReport(request: ServerRequest): Mono<ServerResponse> {
        return request
                .body(BodyExtractors.toMultipartData())
                // TODO: walidacja parametrów
                .map { ImportReportRequest(it.getFirst("file") as FilePart, (it.getFirst("format") as FormFieldPart).value()) }
                .doOnNext { log.info("Request to import Coverage report [report={}, format={}]", it.file.filename(), it.format) }
                .map { reportFormatParserFactory.getParser(it.format).parse(it.file) }
                .map {  }
                .flatMap { ServerResponse.ok().build() }
    }

}