package com.montebruni.holder.stock.infrastructure.rest.provider.brapi

import com.github.tomakehurst.wiremock.client.WireMock
import com.montebruni.holder.configuration.BaseHTTPClientIT
import com.montebruni.holder.configuration.stubGet
import com.montebruni.holder.fixtures.createFindByTickerResponse
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception.BrapiNotFoundException
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.response.BrapiResponse
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.response.FindByTickerResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class BrapiHttpClientIT(
    @Autowired private val brapiHttpClient: BrapiHttpClient
) : BaseHTTPClientIT() {

    private val baseUrl = "/api"

    @Test
    fun `should successfully call brapi and return a ticker`() {
        val ticker = "AAPL"

        val expectedResult = BrapiResponse<FindByTickerResponse>(
            results = listOf(createFindByTickerResponse().copy(ticker = ticker))
        )

        val url = "$baseUrl/quote/$ticker"

        wmServer.stubGet(
            url = url,
            responseBody = mapper.writeValueAsString(expectedResult)
        )

        val result = brapiHttpClient.findByTicker(ticker).results.first()

        assertEquals(expectedResult.results.first().ticker, result.ticker)
        assertEquals(expectedResult.results.first().price, result.price)
        assertEquals(expectedResult.results.first().name, result.name)

        wmServer.verify(
            WireMock.getRequestedFor(WireMock.urlEqualTo(url))
        )
    }

    @Test
    fun `should return status code 404 when ticker is not found`() {
        val ticker = "AAPL"

        val url = "$baseUrl/quote/$ticker"
        val errorMessage = "Ticker not found"
        val response = """
            {
                "error": true,
                "message": "$errorMessage"
            }
        """.trimIndent()

        wmServer.stubGet(
            url = url,
            responseBody = response,
            responseStatus = HttpStatus.NOT_FOUND.value()
        )

        assertThrows<BrapiNotFoundException> { brapiHttpClient.findByTicker(ticker) }.run {
            assertTrue(message!!.contains(errorMessage))
        }

        wmServer.verify(
            WireMock.getRequestedFor(WireMock.urlEqualTo(url))
        )
    }
}
