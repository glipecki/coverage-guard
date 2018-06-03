package net.lipecki.covgrd.coverageguard.report.add.jacoco.xml

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@Suppress("unused")
data class ClassXml(
        @field:XmlAttribute val name: String,
        @field:XmlElement(name = "method") val methods: List<MethodXml>,
        @field:XmlElement(name = "counter") val counters: List<CounterXml>) {
    constructor() : this(
            name = "",
            methods = ArrayList(),
            counters = ArrayList()
    )
}