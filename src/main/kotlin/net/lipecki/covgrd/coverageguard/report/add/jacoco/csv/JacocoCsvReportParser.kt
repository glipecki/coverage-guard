package net.lipecki.covgrd.coverageguard.report.add.jacoco.csv

import net.lipecki.covgrd.coverageguard.logger
import net.lipecki.covgrd.coverageguard.report.ClassCoverage
import net.lipecki.covgrd.coverageguard.report.CoverageStat
import net.lipecki.covgrd.coverageguard.report.CoverageStatValue
import net.lipecki.covgrd.coverageguard.report.add.ReportParser
import org.springframework.core.ResolvableType
import org.springframework.core.codec.StringDecoder
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class JacocoCsvReportParser : ReportParser {

    val log by logger()

    override fun getFormatName(): String = "jacoco/csv"

    override fun parse(content: Flux<DataBuffer>): Flux<ClassCoverage> = StringDecoder.textPlainOnly()
            .decode(content, ResolvableType.NONE, null, null)
            .skip(1)
            .map { it.split(",") }
            .filter {
                if (it.size == 13) {
                    true
                } else {
                    log.warn("CSV report line skipped due to missing record columns [line={}]", it)
                    false
                }
            }
            .map { parseLine(it) }

    /**
     * 0. GROUP
     * 1. PACKAGE
     * 2. CLASS
     * 3. INSTRUCTION_MISSED
     * 4. INSTRUCTION_COVERED
     * 5. BRANCH_MISSED
     * 6. BRANCH_COVERED
     * 7. LINE_MISSED
     * 8. LINE_COVERED
     * 9. COMPLEXITY_MISSED
     * 10. COMPLEXITY_COVERED
     * 11. METHOD_MISSED
     * 12. METHOD_COVERED
     */
    private fun parseLine(parts: List<String>): ClassCoverage = ClassCoverage(
            groupName = parts[0],
            packageName = parts[1],
            className = parts[2],
            coverage = CoverageStat(
                    instructionStat = CoverageStatValue(
                            missed = parts[3].toInt(),
                            covered = parts[4].toInt()
                    ),
                    branchStat = CoverageStatValue(
                            missed = parts[5].toInt(),
                            covered = parts[6].toInt()
                    ),
                    lineStat = CoverageStatValue(
                            missed = parts[7].toInt(),
                            covered = parts[8].toInt()
                    ),
                    complexityStat = CoverageStatValue(
                            missed = parts[9].toInt(),
                            covered = parts[10].toInt()
                    ),
                    methodStat = CoverageStatValue(
                            missed = parts[11].toInt(),
                            covered = parts[12].toInt()
                    ),
                    classStatStat = CoverageStatValue(
                            missed = 0,
                            covered = 0
                    )
            ),
            methods = ArrayList()
    )

}