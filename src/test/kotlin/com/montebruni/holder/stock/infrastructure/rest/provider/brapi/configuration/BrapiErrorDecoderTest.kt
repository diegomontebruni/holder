package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.configuration

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.infrastructure.configuration.JacksonConfiguration
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception.BrapiException
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception.BrapiException.BrapiErrorMessage
import feign.Request
import feign.RequestTemplate
import feign.Response
import feign.RetryableException
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.charset.Charset

class BrapiErrorDecoderTest(
    @MockK private val requestTemplate: RequestTemplate
) : UnitTests() {

    private val mapper = JacksonConfiguration().defaultObjectMapper()
    private val brapiErrorDecoder = BrapiErrorDecoder(mapper)
    private val request = Request.create(
        Request.HttpMethod.GET,
        "url",
        emptyMap(),
        "".toByteArray(),
        Charset.defaultCharset(),
        requestTemplate
    )

    @Test
    fun `should return retryable exception when status is 500`() {
        val response = Response.builder()
            .status(500)
            .request(request)
            .build()

        val exception = brapiErrorDecoder.decode("methodKey", response)

        assertTrue(exception is RetryableException)
    }

    @Test
    fun `should return brapi exception when status is not retryable`() {
        val expectedException = BrapiErrorMessage("message")

        val response = Response.builder()
            .status(400)
            .request(request)
            .body(mapper.writeValueAsBytes(expectedException))
            .build()

        val exception = brapiErrorDecoder.decode("methodKey", response)

        assertEquals(expectedException.message, exception.message)
    }

    @Test
    fun `should return brapi exception with unknown message when response message is not expected`() {
        val response = Response.builder()
            .status(400)
            .request(request)
            .body("".toByteArray())
            .build()

        val exception = brapiErrorDecoder.decode("methodKey", response) as BrapiException

        assertEquals("unknown error message", exception.message)
    }

    @ParameterizedTest
    @CsvSource(
        "400, BrapiBadRequestException",
        "401, BrapiUnauthorizedException",
        "402, BrapiPaymentRequiredException",
        "404, BrapiNotFoundException",
        "403, BrapiUnknownErrorException"
    )
    fun `should return brapi exception with correct status code`(status: Int, expectedException: String) {
        val response = Response.builder()
            .status(status)
            .request(request)
            .build()

        val exception = brapiErrorDecoder.decode("methodKey", response)

        assertEquals(expectedException, exception::class.simpleName)
    }
}
