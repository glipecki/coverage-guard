package net.lipecki.covgrd.coverageguard.report

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import kotlin.collections.HashMap

const val ReportCollection = "reports"

enum class ReportState {

    PENDING, DONE

}

@Document(collection = ReportCollection)
data class Report(
        @Id val id: String? = null,
        @Indexed val uuid: String,
        @Indexed val project: String,
        @Indexed val branch: String,
        val reportDate: Date,
        val state: ReportState,
        val consumers: MutableMap<String, Any> = HashMap()
) {

    val projectQualifiedName: String = "$project:$branch"

    fun withState(state: ReportState) = Report(
            id = id,
            uuid = uuid,
            project = project,
            branch = branch,
            reportDate = reportDate,
            state = state
    )

}