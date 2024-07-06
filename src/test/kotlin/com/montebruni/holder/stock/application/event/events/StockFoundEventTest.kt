package com.montebruni.holder.stock.application.event.events

import com.montebruni.holder.fixtures.createStock
import com.montebruni.holder.stock.domain.event.StockFoundEventData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StockFoundEventTest {

    @Test
    fun `should create StockFoundEvent`() {
        val stock = createStock()
        val eventData = StockFoundEventData(stock.ticker, stock.price, stock.name)

        val event = StockFoundEvent(eventData)

        assertEquals(eventData, event.data)
    }
}
