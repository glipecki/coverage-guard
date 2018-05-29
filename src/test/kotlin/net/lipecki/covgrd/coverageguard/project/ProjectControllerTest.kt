package net.lipecki.covgrd.coverageguard.project

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

@RunWith(SpringRunner::class)
@WebFluxTest
class ProjectControllerTest {

    @Autowired
    private lateinit var client: WebTestClient

    @MockBean
    private lateinit var projectRepository: ProjectRepository

    @Test
    fun `should get projects from repository`() {
        given(projectRepository.findAll()).willReturn(Flux.just(Project()));

        client.get()
                .uri("/project")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Project::class.java).returnResult().apply {
                    assertThat(responseBody).hasSize(1)
                }
    }

}