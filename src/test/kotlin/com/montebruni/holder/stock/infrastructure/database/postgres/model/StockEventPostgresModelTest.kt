package com.montebruni.holder.stock.infrastructure.database.postgres.model

import com.montebruni.holder.fixtures.createStockEvent
import com.montebruni.holder.fixtures.createStockEventPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class StockEventPostgresModelTest {

    @Test
    fun `should convert from stock to model`() {
        val stockEvent = createStockEvent()
        val model = StockEventPostgresModel.fromStockEvent(stockEvent)

        assertEquals(stockEvent.ticker, model.ticker)
        assertEquals(stockEvent.type.name, model.type)
        assertEquals(stockEvent.amount.value.toDouble(), model.amount)
        assertEquals(stockEvent.description, model.description)
        assertEquals(stockEvent.paymentDate, model.paymentDate)
        assertEquals(stockEvent.approvedAt, model.approvedAt)
        assertNotNull(model.id)
        assertNotNull(model.createdAt)
    }

    @Test
    fun `should convert from model to stock`() {
        val model = createStockEventPostgresModel()
        val stockEvent = model.toStockEvent()

        assertEquals(model.ticker, stockEvent.ticker)
        assertEquals(model.type, stockEvent.type.name)
        assertEquals(model.amount, stockEvent.amount.value.toDouble())
        assertEquals(model.description, stockEvent.description)
        assertEquals(model.paymentDate, stockEvent.paymentDate)
        assertEquals(model.approvedAt, stockEvent.approvedAt)
    }
}
