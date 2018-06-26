package net.lipecki.covgrd.coverageguard.report.add

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/report")
class AddReportRestController(val addReportService: AddReportService) {

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

}