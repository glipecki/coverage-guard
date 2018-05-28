package net.lipecki.covgrd.coverageguard.report.import

import org.springframework.stereotype.Component

@Component
class ReportFormatParserFactory(parsers: List<ReportFormatParser>) {

    val parserByName = HashMap<String, ReportFormatParser>()

    init {
        parsers.forEach {
            parserByName[it.getFormatName()] = it
        }
    }

    fun getParser(formatName: String): ReportFormatParser = parserByName[formatName] ?: throw RuntimeException("Missing format parser for type [type=$formatName]")

}