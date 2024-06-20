package com.montebruni.holder.stock.infrastructure.database.postgres

import com.montebruni.holder.configuration.DatabaseIT
import com.montebruni.holder.fixtures.createStockPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
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
            val result = repository.findByIdOrNull(model.id)

            assertNotNull(result)
            assertEquals(model.id, result!!.id)
            assertEquals(model.symbol, result.symbol)
            assertEquals(model.price, result.price)
            assertEquals(model.createdAt, result.createdAt)
            assertEquals(model.updatedAt, result.updatedAt)
        }
    }

    @Nested
    inner class FindBySymbolCases {

        @Test
        fun `should find stock by symbol`() {
            val model = createStockPostgresModel().also(repository::save)
            val result = repository.findBySymbol(model.symbol)

            assertNotNull(result)
            assertEquals(model.id, result!!.id)
            assertEquals(model.symbol, result.symbol)
            assertEquals(model.price, result.price)
            assertEquals(model.createdAt, result.createdAt)
            assertEquals(model.updatedAt, result.updatedAt)
        }

        @Test
        fun `should return null when stock not found`() {
            val result = repository.findBySymbol("NOT_FOUND")

            assertNull(result)
        }
    }
}
