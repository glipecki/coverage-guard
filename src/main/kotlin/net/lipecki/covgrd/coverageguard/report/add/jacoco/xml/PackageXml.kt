package net.lipecki.covgrd.coverageguard.report.add.jacoco.xml

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@Suppress("unused")
data class PackageXml(
        @field:XmlAttribute val name: String,
        @field:XmlElement(name = "class") val classes: List<ClassXml>) {
    constructor() : this(
            name = "",
            classes = ArrayList()
    )
}