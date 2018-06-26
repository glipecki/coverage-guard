package net.lipecki.covgrd.coverageguard.report.add.jacoco.xml

import net.lipecki.covgrd.coverageguard.coverage.ClassCoverage
import net.lipecki.covgrd.coverageguard.coverage.CoverageStat
import net.lipecki.covgrd.coverageguard.coverage.CoverageStatValue
import net.lipecki.covgrd.coverageguard.coverage.MethodCoverage
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.core.io.ClassPathResource
import java.util.*
import java.util.stream.Collectors

class JacocoXmlReportParserTest {

    private val parser = JacocoXmlReportParser()

    @Test
    fun `should parse xml report`() {
        // given
        val sourceXml = ClassPathResource("reports/one-class-report.xml").inputStream

        // when
        val report = parser.parse(sourceXml).collect(Collectors.toList())

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
                                missed = 1
                        )
                ),
                methods = Arrays.asList(
                        MethodCoverage(
                                name = "<init>",
                                description = "(Ljava/lang/String;)V",
                                coverage = CoverageStat(
                                        instructionStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 6
                                        ),
                                        branchStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 0
                                        ),
                                        lineStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 3
                                        ),
                                        complexityStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 1
                                        ),
                                        methodStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 1
                                        ),
                                        classStatStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 0
                                        )
                                )
                        ),
                        MethodCoverage(
                                name = "validate",
                                description = "(Ljava/lang/String;)Lnet/lipecki/validation/ValidatorResult;",
                                coverage = CoverageStat(
                                        instructionStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 11
                                        ),
                                        branchStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 4
                                        ),
                                        lineStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 3
                                        ),
                                        complexityStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 3
                                        ),
                                        methodStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 1
                                        ),
                                        classStatStat = CoverageStatValue(
                                                covered = 0,
                                                missed = 0
                                        )
                                )
                        )
                )
        )
    }

}