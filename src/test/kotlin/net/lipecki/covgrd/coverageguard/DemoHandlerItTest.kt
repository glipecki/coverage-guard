package net.lipecki.covgrd.coverageguard

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureWebTestClient
class DemoHandlerItTest {

    @Autowired
    private lateinit var client: WebTestClient

    @Test
    fun `should get message from service`() {
        client.get()
                .uri("/demo")
                .exchange()
                .expectStatus().isOk
                .expectBody(String::class.java).returnResult().apply {
                    assertThat(responseBody).isEqualTo("Hello...")
                }
    }

}