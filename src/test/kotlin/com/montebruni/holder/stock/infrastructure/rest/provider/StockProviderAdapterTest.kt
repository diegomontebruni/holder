package com.montebruni.holder.stock.infrastructure.rest.provider

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createFindByTickerResponse
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.BrapiHttpClient
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.response.BrapiResponse
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.response.FindByTickerResponse
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class StockProviderAdapterTest(
    @MockK private val brapiHttpClient: BrapiHttpClient
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: StockProviderAdapter

    @Test
    fun `should get stock by ticker`() {
        val ticker = "AAPL"
        val providerResponse = BrapiResponse<FindByTickerResponse>(
            results = listOf(createFindByTickerResponse().copy(ticker = ticker))
        )

        every { brapiHttpClient.findByTicker(ticker) } returns providerResponse

        val response = adapter.getByTicker(ticker)

        val providerResponseResult = providerResponse.results.first()
        assertEquals(ticker, response?.ticker)
        assertEquals(providerResponseResult.name, response?.name)
        assertEquals(providerResponseResult.price, response?.price?.value?.toDouble())

        verify(exactly = 1) { brapiHttpClient.findByTicker(ticker) }
    }

    @Test
    fun `should return null when result is empty`() {
        val ticker = "AAPL"
        val providerResponse = BrapiResponse<FindByTickerResponse>(results = emptyList())

        every { brapiHttpClient.findByTicker(ticker) } returns providerResponse

        assertNull(adapter.getByTicker(ticker))

        verify(exactly = 1) { brapiHttpClient.findByTicker(ticker) }
    }
}
