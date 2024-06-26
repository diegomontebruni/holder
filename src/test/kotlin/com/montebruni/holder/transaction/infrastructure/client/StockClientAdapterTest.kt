package com.montebruni.holder.transaction.infrastructure.client

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.stock.domain.repositories.StockRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StockClientAdapterTest(
    @MockK private val stockRepository: StockRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: StockClientAdapter

    private val ticker = "AAPL"

    @Test
    fun `should return true when exists by ticker`() {
        every { stockRepository.findByTicker(ticker) } returns mockk()

        assertTrue(adapter.existsByTicker(ticker))

        verify(exactly = 1) { stockRepository.findByTicker(ticker) }
    }

    @Test
    fun `should return false when not exists by ticker`() {
        every { stockRepository.findByTicker(ticker) } returns null

        assertFalse(adapter.existsByTicker(ticker))

        verify(exactly = 1) { stockRepository.findByTicker(ticker) }
    }
}
