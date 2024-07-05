package com.montebruni.holder.stock.infrastructure.database.postgres.model

import com.montebruni.holder.fixtures.createStock
import com.montebruni.holder.fixtures.createStockPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class StockPostgresModelTest {

    @Test
    fun `should create model from stock`() {
        val stock = createStock()
        val model = StockPostgresModel.fromStock(stock)

        assertEquals(stock.ticker, model.ticker)
        assertEquals(stock.price.value.toDouble(), model.price)
        assertEquals(stock.name, model.name)
        assertNotNull(model.createdAt)
        assertNotNull(model.updatedAt)
    }

    @Test
    fun `should create stock from model`() {
        val model = createStockPostgresModel()
        val stock = model.toStock()

        assertEquals(model.ticker, stock.ticker)
        assertEquals(model.price, stock.price.value.toDouble())
        assertEquals(model.name, stock.name)
    }
}
