package net.lipecki.covgrd.coverageguard.report.add

import net.lipecki.covgrd.coverageguard.report.ClassCoverage
import net.lipecki.covgrd.coverageguard.report.CoverageStat
import net.lipecki.covgrd.coverageguard.report.CoverageStatValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ExposeClassQualifiedNameTest {

    @Test
    fun `should expose class qualified name`() {
        // given
        val classCoverage = ClassCoverage(
                groupName = "group-name",
                packageName = "net.lipecki",
                className = "SampleClass",
                coverage = anyCoverageState(),
                methods = ArrayList()
        )

        // then
        assertThat(classCoverage.qualifiedName).isEqualTo("group-name:net.lipecki.SampleClass")
    }

    private fun anyCoverageState(): CoverageStat {
        return CoverageStat(
                CoverageStatValue(0, 0),
                CoverageStatValue(0, 0),
                CoverageStatValue(0, 0),
                CoverageStatValue(0, 0),
                CoverageStatValue(0, 0),
                CoverageStatValue(0, 0)
        )
    }

}