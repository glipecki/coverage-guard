package net.lipecki.covgrd.coverageguard.project

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class ProjectRepository {

    fun findAll(): Flux<Project> = Flux.just(Project(), Project(), Project())

}