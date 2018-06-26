package net.lipecki.covgrd.coverageguard.classmonitor

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

const val ClassMonitorCollectionName = "classMonitorStatus"

@Document(collection = ClassMonitorCollectionName)
data class ClassCoverageMonitorStatus(
        @Id var id: String? = null,
        val report: String,
        val project: String,
        val branch: String,
        val groupName: String,
        val packageName: String,
        val className: String,
        val date: Date,
        val methodsCovered: Int,
        val methodsMissed: Int
) {
    @Indexed val projectQualifiedName: String = "$project:$branch"
    @Indexed val classQualifiedName: String = "$projectQualifiedName:$groupName:$packageName:$className"
}