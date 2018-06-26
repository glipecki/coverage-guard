package net.lipecki.covgrd.coverageguard.report.add.jacoco.xml

import javax.xml.bind.annotation.XmlAttribute

@Suppress("unused")
data class CounterXml(
        @field:XmlAttribute val type: String,
        @field:XmlAttribute val covered: Int,
        @field:XmlAttribute val missed: Int) {
    constructor() : this(
            type = "",
            covered = 0,
            missed = 0
    )
}