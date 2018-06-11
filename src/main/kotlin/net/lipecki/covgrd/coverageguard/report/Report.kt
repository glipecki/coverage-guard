package net.lipecki.covgrd.coverageguard.report

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

const val ReportCollection = "reports"

enum class ReportState {

    PENDING, IN_PROGRESS, DONE

}

@Document(collection = ReportCollection)
data class Report(
        @Id val id: String? = null,
        @Indexed val uuid: String,
        val project: String,
        val branch: String,
        val reportDate: Date,
        val state: ReportState
) {

    fun withState(state: ReportState) = Report(
            id = id,
            uuid = uuid,
            project = project,
            branch = branch,
            reportDate = reportDate,
            state = state
    )

}