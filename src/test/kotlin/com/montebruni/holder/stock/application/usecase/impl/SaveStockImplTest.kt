package com.montebruni.holder.stock.application.usecase.impl

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createSaveStockInput
import com.montebruni.holder.fixtures.createStock
import com.montebruni.holder.stock.domain.entity.Stock
import com.montebruni.holder.stock.domain.repositories.StockRepository
import com.montebruni.holder.stock.domain.valueobject.Amount
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class SaveStockImplTest(
    @MockK private val stockRepository: StockRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: SaveStockImpl

    @Test
    fun `should create stock when not found`() {
        val input = createSaveStockInput()
        val stockSlot = slot<Stock>()

        every { stockRepository.findByTicker(input.ticker) } returns null
        every { stockRepository.save(capture(stockSlot)) } answers { stockSlot.captured }

        usecase.execute(input)

        assertEquals(input.ticker, stockSlot.captured.ticker)
        assertEquals(input.price, stockSlot.captured.price)
        assertEquals(input.name, stockSlot.captured.name)

        verify(exactly = 1) {
            stockRepository.findByTicker(input.ticker)
            stockRepository.save(capture(stockSlot))
        }
    }

    @Test
    fun `should update stock when found`() {
        val stock = createStock()
        val input = createSaveStockInput().copy(
            ticker = stock.ticker,
            price = Amount(400.0),
            name = stock.name
        )
        val stockSlot = slot<Stock>()

        every { stockRepository.findByTicker(input.ticker) } returns stock
        every { stockRepository.save(capture(stockSlot)) } answers { stockSlot.captured }

        usecase.execute(input)

        assertEquals(input.ticker, stockSlot.captured.ticker)
        assertEquals(input.price, stockSlot.captured.price)
        assertEquals(input.name, stockSlot.captured.name)

        assertEquals(stock.ticker, stockSlot.captured.ticker)
        assertNotEquals(stock.price, stockSlot.captured.price)
        assertEquals(stock.name, stockSlot.captured.name)

        verify(exactly = 1) {
            stockRepository.findByTicker(input.ticker)
            stockRepository.save(capture(stockSlot))
        }
    }
}
