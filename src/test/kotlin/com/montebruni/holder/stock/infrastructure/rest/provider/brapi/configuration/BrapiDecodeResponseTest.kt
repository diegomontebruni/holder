package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.configuration

import com.montebruni.holder.infrastructure.configuration.JacksonConfiguration
import feign.Request
import feign.RequestTemplate
import feign.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class BrapiDecodeResponseTest {

    private val decoder = BrapiDecodeResponse(JacksonConfiguration().defaultObjectMapper())

    @Test
    fun `should decode response if have result in content`() {
        val json = """
            {
                "results": [
                    {
                        "ticker": "AAPL"
                    }
                ]
            }
        """.trimIndent()

        val request = Request.create(
            Request.HttpMethod.GET,
            "http://localhost:8080/api/quote/AAPL",
            emptyMap(),
            null,
            Charset.defaultCharset(),
            RequestTemplate()
        )

        val response = Response
            .builder()
            .request(request)
            .status(200)
            .reason("OK")
            .body(json, StandardCharsets.UTF_8)
            .build()

        val decoded = decoder.decode(response, PayloadTest::class.java) as PayloadTest

        assertEquals("AAPL", decoded.ticker)
    }

    @Test
    fun `should return string when content-type is not json`() {
        val request = Request.create(
            Request.HttpMethod.GET,
            "http://localhost:8080/api/quote/test",
            emptyMap(),
            null,
            Charset.defaultCharset(),
            RequestTemplate()
        )

        val response = Response
            .builder()
            .request(request)
            .status(200)
            .reason("OK")
            .headers(mapOf("Content-Type" to listOf("text/plain")))
            .body("AAPL", StandardCharsets.UTF_8)
            .build()

        val decoded = decoder.decode(response, PayloadTest::class.java) as String

        assertEquals("AAPL", decoded)
    }

    private data class PayloadTest(val ticker: String)
}
