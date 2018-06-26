package net.lipecki.covgrd.coverageguard.report.add

import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.eq
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.given
import org.mockito.Captor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ClassPathResource
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.InputStream
import java.util.*


@RunWith(SpringRunner::class)
@WebMvcTest(AddReportRestController::class)
class AddReportRestControllerTest {

    private val format = "sample-test-format"
    private val branch = "sample-test-branch"
    private val project = "sample-test-project"
    private val sourceXml = ClassPathResource("reports/empty-report.xml")

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var service: AddReportService

    @Captor
    private lateinit var contentCaptor: ArgumentCaptor<InputStream>

    @Test
    fun `should fail on missing params - file`() {
        mockMvc.perform(
                multipart("/report/add")
                        .param("format", format)
                        .param("project", project)
                        .param("branch", branch)
        ).andExpect(
                status().isBadRequest
        )
    }

    @Test
    fun `should fail on missing params - format`() {
        mockMvc.perform(
                multipart("/report/add")
                        .file(MockMultipartFile("file", sourceXml.inputStream))
                        .param("project", project)
                        .param("branch", branch)
        ).andExpect(
                status().isBadRequest
        )
    }

    @Test
    fun `should fail on missing params - project`() {
        mockMvc.perform(
                multipart("/report/add")
                        .file(MockMultipartFile("file", sourceXml.inputStream))
                        .param("format", format)
                        .param("branch", branch)
        ).andExpect(
                status().isBadRequest
        )
    }

    @Test
    fun `should pass data to add report service`() {
        val expectedResult = UUID.randomUUID().toString()

        // given
        given(
                service.addReport(
                        eq(project),
                        eq(branch),
                        eq(format),
                        capture(contentCaptor)
                )
        ).willReturn(expectedResult)

        // when
        val result = mockMvc.perform(
                multipart("/report/add")
                        .file(MockMultipartFile("file", sourceXml.inputStream))
                        .param("format", format)
                        .param("project", project)
                        .param("branch", branch)
        ).andExpect(
                status().isOk
        ).andReturn().response.contentAsString

        // then
        assertThat(result).isEqualTo(expectedResult)

//        val firstLine = StringDecoder.textPlainOnly()
//                .decode(contentCaptor.value, ResolvableType.NONE, null, null)
//                .blockFirst()
//        val sourceXmlFirstLine = sourceXml.inputStream.bufferedReader().readLine()
//        assertThat(firstLine).isEqualTo(sourceXmlFirstLine)
    }

}