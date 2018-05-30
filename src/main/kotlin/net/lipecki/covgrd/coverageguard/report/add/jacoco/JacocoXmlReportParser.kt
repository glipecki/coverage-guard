package net.lipecki.covgrd.coverageguard.report.add.jacoco

import net.lipecki.covgrd.coverageguard.logger
import net.lipecki.covgrd.coverageguard.report.Report
import net.lipecki.covgrd.coverageguard.report.add.ReportFormatParser
import org.springframework.core.ResolvableType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.codec.xml.Jaxb2XmlDecoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlType

@XmlRootElement(name = "report")
data class ReportXml(@XmlAttribute val name: String) {
    constructor() : this(name = "jaxb-deserializer")
}

@Component
class JacocoXmlReportParser : ReportFormatParser {

    val log by logger()

    override fun getFormatName(): String = "jacoco/csv"

    override fun parse(file: FilePart): Mono<Report> {
        Jaxb2XmlDecoder()
                .decode(file.content(), ResolvableType.forType(ReportXml::class.java), null, null)
                .doOnError { log.error("error: {}", it) }
                .subscribe { log.info("Result: {}", it) }
        return Mono.just(Report(UUID.randomUUID(), Date(), "c", emptyArray()))
    }

//    private fun decodeTextPlainString(content: Publisher<DataBuffer>): Flux<String> = StringDecoder.textPlainOnly().decode(content, ResolvableType.NONE, null, null)

}