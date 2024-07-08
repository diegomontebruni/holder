package com.montebruni.holder.stock.presentation.interfaces

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createGetStockInput
import com.montebruni.holder.fixtures.createGetStockOutput
import com.montebruni.holder.stock.application.usecase.GetStock
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class StockInterfaceTest(
    @MockK private val getStock: GetStock
) : UnitTests() {

    @InjectMockKs
    private lateinit var stockInterface: StockInterface

    @Test
    fun `should get stock by ticker`() {
        val ticker = "AAPL"
        val stockInput = createGetStockInput().copy(ticker = ticker)
        val stock = createGetStockOutput().copy(ticker = ticker)

        every { getStock.execute(stockInput) } returns stock

        val response = stockInterface.getStockByTicker(ticker)

        assertEquals(stock.ticker, response?.ticker)
        assertEquals(stock.name, response?.name)
        assertEquals(stock.price.value.toDouble(), response?.price)

        verify(exactly = 1) { getStock.execute(stockInput) }
    }

    @Test
    fun `should return null when ticker no found`() {
        val ticker = "AAPL"
        val stockInput = createGetStockInput().copy(ticker = ticker)

        every { getStock.execute(stockInput) } returns null

        assertNull(stockInterface.getStockByTicker(ticker))

        verify(exactly = 1) { getStock.execute(stockInput) }
    }
}
