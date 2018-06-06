package net.lipecki.covgrd.coverageguard.report.add

import net.lipecki.covgrd.coverageguard.logger
import org.springframework.http.HttpStatus
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.codec.multipart.FormFieldPart
import org.springframework.http.codec.multipart.Part
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

private data class AddReportRequest(
        val project: String,
        val branch: String,
        val file: FilePart,
        val format: String
)

@Component
class AddReportHandler(val addReportService: AddReportService) {

    val log by logger()

    fun importReport(request: ServerRequest): Mono<ServerResponse> {
        return request
                .body(BodyExtractors.toMultipartData())
                .map {
                    AddReportRequest(
                            getRequiredParam<FormFieldPart>(it, "project").value(),
                            getRequiredParam<FormFieldPart>(it, "branch").value(),
                            getRequiredParam(it, "file"),
                            getRequiredParam<FormFieldPart>(it, "format").value()
                    )
                }
                .doOnNext { log.info("Request to import Coverage report [report={}, format={}]", it.file.filename(), it.format) }
                .map { addReportService.addReport(it.project, it.branch, it.file.content(), it.format) }
                .flatMap { ServerResponse.ok().body(it, String::class.java) }
                .doOnError { log.error("Can't handle method report add", it) }
    }

    private inline fun <reified T : Part> getRequiredParam(map: MultiValueMap<String, Part>, name: String): T {
        val param = map.getFirst(name) ?: throw missingParam(name)
        if (param is T) {
            return param
        } else {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong parameter type for param: $name")
        }
    }

    private fun missingParam(name: String) = ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing request param: $name")

}