package net.lipecki.covgrd.coverageguard

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureWebTestClient
abstract class BaseItTest {

    @Test
    fun `should load context`() {
        assertThat(appContext).isNotNull
    }

    @Autowired
    private lateinit var appContext: ApplicationContext

}
