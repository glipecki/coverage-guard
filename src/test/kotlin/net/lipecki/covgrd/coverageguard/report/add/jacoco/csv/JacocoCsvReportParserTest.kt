package net.lipecki.covgrd.coverageguard.report.add.jacoco.csv

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import net.lipecki.covgrd.coverageguard.coverage.CoverageStat
import net.lipecki.covgrd.coverageguard.coverage.CoverageStatValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import reactor.core.publisher.Flux
import java.util.*

class JacocoCsvReportParserTest {

    private val parser = JacocoCsvReportParser()

    @Test
    fun `should parse csv report`() {
        // given
        val sourceXml = parseXml("reports/one-class-report.csv")

        // when
        val report = parser.parse(sourceXml)

        // then
        assertThat(report[0]).isEqualTo(expectedClassReport())
    }

    private fun parseXml(fileName: String): Flux<DataBuffer> = DataBufferUtils.readInputStream({ ClassPathResource(fileName).inputStream }, DefaultDataBufferFactory(), 100)


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