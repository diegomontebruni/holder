package com.montebruni.holder.stock.infrastructure.event.listener

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createStock
import com.montebruni.holder.stock.application.event.events.StockFoundEvent
import com.montebruni.holder.stock.application.usecase.SaveStock
import com.montebruni.holder.stock.application.usecase.input.SaveStockInput
import com.montebruni.holder.stock.domain.event.StockFoundEventData
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StockFoundEventListenerTest(
    @MockK private val saveStock: SaveStock
) : UnitTests() {

    @InjectMockKs
    private lateinit var listener: StockFoundEventListener

    @Test
    fun `should call use case successfully`() {
        val useCaseInputSlot = slot<SaveStockInput>()
        val event = createStock()
            .let {
                StockFoundEventData(
                    ticker = it.ticker,
                    name = it.name,
                    price = it.price
                )
            }.let(::StockFoundEvent)

        justRun { saveStock.execute(capture(useCaseInputSlot)) }

        listener.handle(event)

        assertEquals(event.data.ticker, useCaseInputSlot.captured.ticker)
        assertEquals(event.data.name, useCaseInputSlot.captured.name)
        assertEquals(event.data.price, useCaseInputSlot.captured.price)

        verify(exactly = 1) { saveStock.execute(useCaseInputSlot.captured) }
    }
}
