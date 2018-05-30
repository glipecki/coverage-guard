package net.lipecki.covgrd.coverageguard.report

import java.util.*

data class MethodReport(
        val name: String,
        val javaName: String
)

data class ClassReport(
        val group: String,
        val pck: String,
        val name: String
)

data class Report(
        val uuid: UUID,
        val date: Date,
        val project: String,
        val classes: Array<ClassReport>
)