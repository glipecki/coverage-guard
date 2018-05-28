package net.lipecki.covgrd.coverageguard.report.import

import org.springframework.http.codec.multipart.FilePart

interface ReportFormatParser {

    fun getFormatName(): String
    fun parse(file: FilePart): Report

}