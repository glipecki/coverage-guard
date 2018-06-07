package net.lipecki.covgrd.coverageguard.consumer

import net.lipecki.covgrd.coverageguard.logger
import net.lipecki.covgrd.coverageguard.report.ClassCoverageReportCollection
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class SampleService(val consumerRepository: ConsumerRepository) {

    private val log by logger()

    @PostConstruct
    fun demo() {
        consumerRepository.findNotConsumedEntityIds(
                "sample",
                ClassCoverageReportCollection,
                "reportUuid",
                100
        ).subscribe {
            log.info("Report to consume? [reportUuid={}]", it)
        }
    }

}