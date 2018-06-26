package net.lipecki.covgrd.coverageguard.classmonitor


data class ClassCoverageSummary(val qualifiedName: String, val methodCovered: Int, val methodMissed: Int)