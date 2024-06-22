package com.montebruni.holder.stock.infrastructure.database.postgres

import com.montebruni.holder.configuration.DatabaseIT
import com.montebruni.holder.fixtures.createStockPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class StockPostgresRepositoryIT(
    @Autowired private val repository: StockPostgresRepository
) : DatabaseIT(repository) {

    @Nested
    inner class SaveCases {

        @Test
        fun `should save stock successfully`() {
            val model = createStockPostgresModel().also(repository::save)
            val result = repository.findByIdOrNull(model.ticker)

            assertNotNull(result)
            assertEquals(model.ticker, result!!.ticker)
            assertEquals(model.ticker, result.ticker)
            assertEquals(model.price, result.price)
            assertEquals(model.createdAt, result.createdAt)
            assertEquals(model.updatedAt, result.updatedAt)
        }

        @Test
        fun `should save stock with diff updated at`() {
            val model = createStockPostgresModel().also(repository::saveAndFlush)
            val updatedModel = model.copy(price = 15.0).let(repository::saveAndFlush)

            assertNotEquals(model.updatedAt, updatedModel.updatedAt)
        }
    }

    @Nested
    inner class FindByTickerCases {

        @Test
        fun `should find stock by ticker`() {
            val model = createStockPostgresModel().also(repository::save)
            val result = repository.findByIdOrNull(model.ticker)

            assertNotNull(result)
            assertEquals(model.ticker, result!!.ticker)
            assertEquals(model.ticker, result.ticker)
            assertEquals(model.price, result.price)
            assertEquals(model.createdAt, result.createdAt)
            assertEquals(model.updatedAt, result.updatedAt)
        }

        @Test
        fun `should return null when stock not found`() {
            val result = repository.findByIdOrNull("NOT_FOUND")

            assertNull(result)
        }
    }
}
