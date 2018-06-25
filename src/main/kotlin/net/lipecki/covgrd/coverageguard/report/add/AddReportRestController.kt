package net.lipecki.covgrd.coverageguard.report.add

import net.lipecki.covgrd.coverageguard.logger
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

private data class AddReportRequest(
        val project: String,
        val branch: String,
        val file: FilePart,
        val format: String
)

@RestController("/report")
class AddReportRestController(val addReportService: AddReportService) {

    val log by logger()

    @PostMapping("/add")
    fun addReport(
            @RequestParam("file") file: MultipartFile,
            @RequestParam format: String,
            @RequestParam branch: String,
            @RequestParam project: String
    ): String = addReportService.addReport(
            project,
            branch,
            format,
            file.inputStream
    )

//        return request
//                .body(BodyExtractors.toMultipartData())
//                .map {
//                    AddReportRequest(
//                            getRequiredParam<FormFieldPart>(it, "project").value(),
//                            getOptionalParam<FormFieldPart>(it, "branch")?.value() ?: "master",
//                            getRequiredParam(it, "file"),
//                            getRequiredParam<FormFieldPart>(it, "format").value()
//                    )
//                }
//                .doOnNext { log.info("Request to import Coverage report [report={}, format={}]", it.file.filename(), it.format) }
//                .map { addReportService.addReport(it.project, it.branch, it.file.content(), it.format) }
//                .flatMap { ServerResponse.ok().body(it, String::class.java) }
//                .doOnError { log.error("Can't handle method report add", it) }

}