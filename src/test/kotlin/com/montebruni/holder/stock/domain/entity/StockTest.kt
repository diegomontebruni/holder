package com.montebruni.holder.stock.domain.entity

import com.montebruni.holder.fixtures.createStock
import com.montebruni.holder.stock.domain.valueobject.Amount
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StockTest {

    @Test
    fun `should update existing stock`() {
        val stock = createStock()
        val newPrice = Amount(100.0)

        val updatedStock = stock.update(newPrice)

        assertEquals(newPrice, updatedStock.price)
        assertEquals(stock.ticker, updatedStock.ticker)
        assertEquals(stock.name, updatedStock.name)
    }
}
