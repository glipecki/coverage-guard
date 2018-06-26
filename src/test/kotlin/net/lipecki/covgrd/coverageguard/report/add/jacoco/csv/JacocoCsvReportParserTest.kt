package net.lipecki.covgrd.coverageguard.report.add.jacoco.csv

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import net.lipecki.covgrd.coverageguard.coverage.CoverageStat
import net.lipecki.covgrd.coverageguard.coverage.CoverageStatValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.core.io.ClassPathResource
import java.util.*
import java.util.stream.Collectors
import kotlin.streams.toList

class JacocoCsvReportParserTest {

    private val parser = JacocoCsvReportParser()

    @Test
    fun `should parse csv report`() {
        // given
        val sourceXml = ClassPathResource("reports/one-class-report.csv").inputStream

        // when
        val report = parser.parse(sourceXml).toList()

        // then
        assertThat(report[0]).isEqualTo(expectedClassReport())
    }


    /**
     * Manually deserialized xml report.
     */
    private fun expectedClassReport(): ClassCoverage {
        return ClassCoverage(
                groupName = "base-model",
                packageName = "net.lipecki.validation",
                className = "WhiteSymbolsAllowedValidator",
                coverage = CoverageStat(
                        instructionStat = CoverageStatValue(
                                covered = 0,
                                missed = 17
                        ),
                        branchStat = CoverageStatValue(
                                covered = 0,
                                missed = 4
                        ),
                        lineStat = CoverageStatValue(
                                covered = 0,
                                missed = 6
                        ),
                        complexityStat = CoverageStatValue(
                                covered = 0,
                                missed = 4
                        ),
                        methodStat = CoverageStatValue(
                                covered = 0,
                                missed = 2
                        ),
                        classStatStat = CoverageStatValue(
                                covered = 0,
                                missed = 0
                        )
                ),
                methods = ArrayList()
        )
    }

}