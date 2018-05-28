package net.lipecki.covgrd.coverageguard

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@RunWith(SpringRunner::class)
@WebFluxTest(DemoHandler::class)
@Import(DemoRouting::class)
class DemoHandlerTest {

    @Autowired
    private lateinit var client: WebTestClient

    @MockBean
    private lateinit var service: DemoService

    @Test
    fun `should get message from service`() {
        val expectedMessage = "expectedMessage"

        given(service.getHello()).willReturn(Mono.just(expectedMessage))

        val result = client.get()
                .uri("/demo/")
                .exchange()
                .expectStatus().isOk
                .expectBody(String::class.java).returnResult().responseBody

        assertThat(result).isEqualTo(expectedMessage)
    }

}