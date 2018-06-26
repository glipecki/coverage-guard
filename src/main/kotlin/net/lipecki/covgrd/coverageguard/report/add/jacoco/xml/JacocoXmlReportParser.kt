package net.lipecki.covgrd.coverageguard.report.add.jacoco.xml

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import net.lipecki.covgrd.coverageguard.coverage.CoverageStat
import net.lipecki.covgrd.coverageguard.coverage.CoverageStatValue
import net.lipecki.covgrd.coverageguard.coverage.MethodCoverage
import net.lipecki.covgrd.coverageguard.report.add.ReportParser
import org.springframework.stereotype.Component
import java.io.InputStream
import java.util.stream.Stream
import javax.xml.bind.JAXBContext

@Component
class JacocoXmlReportParser : ReportParser {

    val jaxbContext = JAXBContext.newInstance(ReportXml::class.java)!!
    
    override fun getFormatName(): String = "jacoco/xml"

    override fun parse(content: InputStream): Stream<ClassCoverage> = mapReport(parseReport(content)).stream()

    private fun parseReport(content: InputStream) = jaxbContext.createUnmarshaller().unmarshal(content) as ReportXml

    private fun mapReport(report: ReportXml) = report.groups.flatMap { mapGroup(it) }

    private fun mapGroup(group: GroupXml) = group.packages.flatMap { mapPackage(it, group) }

    private fun mapPackage(classPackage: PackageXml, group: GroupXml) = classPackage.classes.map {
        ClassCoverage(
                groupName = group.name,
                className = stripToLastSegment(normalizeSegmentSeparator(it.name)),
                packageName = normalizeSegmentSeparator(classPackage.name),
                coverage = mapCoverage(it.counters),
                methods = mapMethods(it)
        )
    }

    private fun normalizeSegmentSeparator(string: String) = string.replace('/', '.')

    private fun stripToLastSegment(string: String) = string.substringAfterLast(".")

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