package net.lipecki.covgrd.coverageguard.coverage

data class ClassCoverage(
        val groupName: String,
        val packageName: String,
        val className: String,
        val coverage: CoverageStat,
        val methods: List<MethodCoverage>
) {
    val qualifiedName: String = "$groupName:$packageName.$className"
}