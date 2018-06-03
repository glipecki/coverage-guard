package net.lipecki.covgrd.coverageguard.report

data class MethodCoverage(
        val name: String,
        val description: String,
        val coverage: CoverageStat
)