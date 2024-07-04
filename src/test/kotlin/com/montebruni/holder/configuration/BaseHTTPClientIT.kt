package com.montebruni.holder.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.common.Slf4jNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.montebruni.holder.infrastructure.configuration.JacksonConfiguration
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(
    classes = [
        JacksonConfiguration::class,
        HttpMessageConvertersAutoConfiguration::class,
        FeignAutoConfiguration::class,
        SecurityAutoConfiguration::class
    ]
)
@EnableFeignClients(basePackages = ["com.montebruni.holder"])
@ContextConfiguration(initializers = [WireMockInitializer::class])
class BaseHTTPClientIT {

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    lateinit var wmServer: WireMockServer

    @Autowired
    lateinit var mapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        wmServer.resetAll()
    }
}

internal class WireMockInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val wmServer = WireMockServer(
            WireMockConfiguration()
                .dynamicPort()
                .notifier(Slf4jNotifier(true))
        )

        wmServer.start()

        applicationContext.beanFactory.registerSingleton("wmServer", wmServer)

        applicationContext.addApplicationListener { applicationEvent ->
            if (applicationEvent is ContextClosedEvent) {
                wmServer.stop()
            }
        }

        val httpClientUrls = mapOf(
            "app.providers.brapi.host" to ""
        ).map { entry -> "${entry.key}=http://localhost:${wmServer.port()}${entry.value}" }

        TestPropertyValues.of(httpClientUrls).applyTo(applicationContext)
    }
}
