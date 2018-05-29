package net.lipecki.covgrd.coverageguard.report.import

import net.lipecki.covgrd.coverageguard.logger
import org.springframework.core.ResolvableType
import org.springframework.core.codec.StringDecoder
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.codec.multipart.FormFieldPart
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

private data class ImportReportRequest(val file: FilePart, val format: String)

@Component
class ReportImportHandler {

    val log by logger()

    fun importReport(request: ServerRequest): Mono<ServerResponse> {
        return request
                .body(BodyExtractors.toMultipartData())
                .map { ImportReportRequest(it.getFirst("file") as FilePart, (it.getFirst("format") as FormFieldPart).value()) }
                .doOnNext { log.info("Request to import Coverage report [report={}]", it.file) }
//                .map {
//                    StringDecoder
//                            .textPlainOnly()
//                            .decode(it.file.content(), ResolvableType.NONE, null, null)
//                }
                .map {  }
                .flatMap { ServerResponse.ok().build() }
    }

}