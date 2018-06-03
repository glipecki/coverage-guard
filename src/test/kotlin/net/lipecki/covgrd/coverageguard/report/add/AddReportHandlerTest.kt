package net.lipecki.covgrd.coverageguard.report.add

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.eq
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.given
import org.mockito.Captor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.core.ResolvableType
import org.springframework.core.codec.StringDecoder
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*


@RunWith(SpringRunner::class)
@WebFluxTest(AddReportHandler::class)
@Import(AddReportRouterConfiguration::class)
class AddReportHandlerTest {

    private val format = "sample-test-format"

    private val project = "sample-test-project"

    @Autowired
    private lateinit var client: WebTestClient

    @MockBean
    private lateinit var service: AddReportService

    @Captor
    private lateinit var contentCaptor: ArgumentCaptor<Flux<DataBuffer>>

    @Test
    fun `should fail on missing params - file`() {
        // given
        val body = MultipartBodyBuilder()
        body.part("format", format)
        body.part("project", project)

        // when & then
        client.post()
                .uri("/report/add")
                .syncBody(body.build())
                .exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `should fail on missing params - format`() {
        // given
        val body = MultipartBodyBuilder()
        body.part("file", ClassPathResource("reports/one-class-report.xml"))
        body.part("project", project)

        // when & then
        client.post()
                .uri("/report/add")
                .syncBody(body.build())
                .exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `should fail on missing params - project`() {
        // given
        val body = MultipartBodyBuilder()
        body.part("file", ClassPathResource("reports/one-class-report.xml"))
        body.part("project", project)

        // when & then
        client.post()
                .uri("/report/add")
                .syncBody(body.build())
                .exchange()
                .expectStatus().isBadRequest
    }

    @Test
    fun `should pass data to add report service`() {
        val sourceXml = ClassPathResource("reports/empty-report.xml")
        val expectedResult = UUID.randomUUID().toString()

        // given
        val body = MultipartBodyBuilder()
        body.part("format", format)
        body.part("file", sourceXml)
        body.part("project", project)
        given(service.addReport(
                any(),
                capture(contentCaptor),
                eq(format)
        )).willReturn(Mono.just(expectedResult))

        // when
        val result = client.post()
                .uri("/report/add")
                .syncBody(body.build())
                .exchange()
                .expectStatus().isOk
                .expectBody(String::class.java).returnResult().responseBody

        // then
        assertThat(result).isEqualTo(expectedResult)

        val firstLine = StringDecoder.textPlainOnly()
                .decode(contentCaptor.value, ResolvableType.NONE, null, null)
                .blockFirst()
        val sourceXmlFirstLine = sourceXml.inputStream.bufferedReader().readLine()
        assertThat(firstLine).isEqualTo(sourceXmlFirstLine)
    }

}