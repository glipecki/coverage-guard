package net.lipecki.covgrd.coverageguard.report.add.jacoco.xml

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@Suppress("unused")
@XmlRootElement(name = "report")
data class ReportXml(
        @field:XmlAttribute val name: String,
        @field:XmlElement(name = "group") val groups: List<GroupXml>) {
    constructor() : this(
            name = "",
            groups = ArrayList()
    )
}