package net.lipecki.covgrd.coverageguard.report.add.jacoco.xml

import net.lipecki.covgrd.coverageguard.logger
import net.lipecki.covgrd.coverageguard.report.ClassCoverage
import net.lipecki.covgrd.coverageguard.report.CoverageStat
import net.lipecki.covgrd.coverageguard.report.CoverageStatValue
import net.lipecki.covgrd.coverageguard.report.MethodCoverage
import net.lipecki.covgrd.coverageguard.report.add.*
import org.springframework.core.ResolvableType
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.codec.xml.Jaxb2XmlDecoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class JacocoXmlReportParser : ReportParser {

    val log by logger()

    override fun getFormatName(): String = "jacoco/xml"

    override fun parse(content: Flux<DataBuffer>): Flux<ClassCoverage> {
        return Jaxb2XmlDecoder()
                .decode(content, ResolvableType.forType(ReportXml::class.java), null, null)
                .map { it as ReportXml }
                .doOnNext { log.trace("Parsed report [report={}]", it) }
                .flatMap { Flux.fromIterable(mapReport(it)) }
    }

    private fun mapReport(report: ReportXml) = report.groups.flatMap { mapGroup(it) }

    private fun mapGroup(group: GroupXml) = group.packages.flatMap { mapPackage(it, group) }

    private fun mapPackage(classPackage: PackageXml, group: GroupXml) = classPackage.classes.map {
        ClassCoverage(
                groupName = group.name,
                className = it.name,
                packageName = classPackage.name,
                coverage = mapCoverage(it.counters),
                methods = mapMethods(it)
        )
    }

    private fun mapMethods(classReport: ClassXml) = classReport.methods.map {
        MethodCoverage(
                name = it.name,
                description = it.desc,
                coverage = mapCoverage(it.counters)
        )
    }

    private fun mapCoverage(counters: List<CounterXml>) = CoverageStat(
            instructionStat = mapCoverageStat(counters, "INSTRUCTION"),
            branchStat = mapCoverageStat(counters, "BRANCH"),
            lineStat = mapCoverageStat(counters, "LINE"),
            complexityStat = mapCoverageStat(counters, "COMPLEXITY"),
            methodStat = mapCoverageStat(counters, "METHOD"),
            classStatStat = mapCoverageStat(counters, "CLASS")
    )

    private fun mapCoverageStat(counters: List<CounterXml>, name: String) = mapCounterToCoverageStat(counters.findLast { it.type == name })

    private fun mapCounterToCoverageStat(counter: CounterXml?) = CoverageStatValue(
            covered = counter?.covered ?: 0,
            missed = counter?.missed ?: 0
    )

}