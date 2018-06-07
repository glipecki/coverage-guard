package net.lipecki.covgrd.coverageguard.coverage

data class CoverageStat(
        val instructionStat: CoverageStatValue,
        val branchStat: CoverageStatValue,
        val lineStat: CoverageStatValue,
        val complexityStat: CoverageStatValue,
        val methodStat: CoverageStatValue,
        val classStatStat: CoverageStatValue
)