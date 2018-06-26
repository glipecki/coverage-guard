package net.lipecki.covgrd.coverageguard.report.add.jacoco.xml

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@Suppress("unused")
data class MethodXml(
        @field:XmlAttribute val name: String,
        @field:XmlAttribute val desc: String,
        @field:XmlAttribute val line: Int,
        @field:XmlElement(name = "counter") val counters: List<CounterXml>) {
    constructor() : this(
            name = "",
            desc = "",
            line = 0,
            counters = ArrayList()
    )
}