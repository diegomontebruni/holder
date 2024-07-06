package com.montebruni.holder.stock.application.usecase.impl

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createGetStockInput
import com.montebruni.holder.fixtures.createStock
import com.montebruni.holder.fixtures.createStockProviderResponse
import com.montebruni.holder.stock.application.event.EventPublisher
import com.montebruni.holder.stock.application.event.events.StockFoundEvent
import com.montebruni.holder.stock.application.provider.StockProvider
import com.montebruni.holder.stock.domain.repositories.StockRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class GetStockImplTest(
    @MockK private val stockRepository: StockRepository,
    @MockK private val stockProvider: StockProvider,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: GetStockImpl

    private val input = createGetStockInput()

    @Test
    fun `should return null when stock is not found`() {
        every { stockRepository.findByTicker(input.ticker) } returns null
        every { stockProvider.getByTicker(input.ticker) } returns null

        val result = usecase.execute(input)

        assertNull(result)

        verify(exactly = 1) {
            stockRepository.findByTicker(input.ticker)
            stockProvider.getByTicker(input.ticker)
        }
        verify(exactly = 0) {
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should return stock from repository`() {
        val stock = createStock()
        every { stockRepository.findByTicker(input.ticker) } returns stock

        val result = usecase.execute(input)

        assertEquals(stock.ticker, result?.ticker)
        assertEquals(stock.price, result?.price)
        assertEquals(stock.name, result?.name)

        verify(exactly = 1) { stockRepository.findByTicker(input.ticker) }
        verify(exactly = 0) {
            stockProvider.getByTicker(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should return stock from provider and send event`() {
        val stockProviderResponse = createStockProviderResponse()
        val eventSlot = slot<StockFoundEvent>()

        every { stockRepository.findByTicker(input.ticker) } returns null
        every { stockProvider.getByTicker(input.ticker) } returns stockProviderResponse
        justRun { eventPublisher.publishEvent(capture(eventSlot)) }

        val result = usecase.execute(input)

        assertEquals(stockProviderResponse.ticker, result?.ticker)
        assertEquals(stockProviderResponse.price, result?.price)
        assertEquals(stockProviderResponse.name, result?.name)

        val eventData = eventSlot.captured.data
        assertEquals(stockProviderResponse.ticker, eventData.ticker)
        assertEquals(stockProviderResponse.price, eventData.price)
        assertEquals(stockProviderResponse.name, eventData.name)

        verify(exactly = 1) {
            stockRepository.findByTicker(input.ticker)
            stockProvider.getByTicker(input.ticker)
            eventPublisher.publishEvent(eventSlot.captured)
        }
    }
}
