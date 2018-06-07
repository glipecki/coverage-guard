package net.lipecki.covgrd.coverageguard.coverage

data class MethodCoverage(
        val name: String,
        val description: String,
        val coverage: CoverageStat
)