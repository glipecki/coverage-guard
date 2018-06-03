package net.lipecki.covgrd.coverageguard.report.add

import org.springframework.stereotype.Component

@Component
class ReportParserFactory(parsers: List<ReportParser>) {

    val parserByName = HashMap<String, ReportParser>()

    init {
        parsers.forEach {
            parserByName[it.getFormatName()] = it
        }
    }

    fun getParser(formatName: String): ReportParser = parserByName[formatName] ?: throw RuntimeException("Missing format parser for type [type=$formatName]")

}