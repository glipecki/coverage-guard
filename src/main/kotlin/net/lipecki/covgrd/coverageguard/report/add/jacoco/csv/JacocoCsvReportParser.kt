package net.lipecki.covgrd.coverageguard.report.add.jacoco.csv

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import net.lipecki.covgrd.coverageguard.coverage.CoverageStat
import net.lipecki.covgrd.coverageguard.coverage.CoverageStatValue
import net.lipecki.covgrd.coverageguard.logger
import net.lipecki.covgrd.coverageguard.report.add.ReportParser
import org.springframework.stereotype.Component
import java.io.InputStream
import java.util.stream.Stream
import kotlin.streams.asStream

@Component
class JacocoCsvReportParser : ReportParser {

    override fun getFormatName(): String = "jacoco/csv"

    override fun parse(content: InputStream): Stream<ClassCoverage> = content
            .bufferedReader()
            .useLines {
                it
                        .filter { !it.contains("GROUP") }
                        .map { it.split(",") }
                        .filter { it.size == 13 }
                        .map { parseLine(it) }
                        .toList()
                        .stream()
            }

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