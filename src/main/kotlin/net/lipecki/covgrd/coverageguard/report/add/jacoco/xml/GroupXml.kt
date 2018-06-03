package net.lipecki.covgrd.coverageguard.report.add.jacoco.xml

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

@Suppress("unused")
data class GroupXml(
        @field:XmlAttribute val name: String,
        @field:XmlElement(name = "package") val packages: List<PackageXml>) {
    constructor() : this(
            name = "",
            packages = ArrayList()
    )
}