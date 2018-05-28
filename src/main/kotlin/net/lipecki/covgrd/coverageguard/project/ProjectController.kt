package net.lipecki.covgrd.coverageguard.project

import net.lipecki.covgrd.coverageguard.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/project")
class ProjectController(private var projectRepository: ProjectRepository) {

    val log by logger()

    @GetMapping
    fun getAllProjects() = projectRepository.findAll()

}