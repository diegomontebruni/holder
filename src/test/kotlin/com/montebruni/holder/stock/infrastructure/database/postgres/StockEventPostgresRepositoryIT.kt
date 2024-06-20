package com.montebruni.holder.stock.infrastructure.database.postgres

import com.montebruni.holder.configuration.DatabaseIT
import com.montebruni.holder.fixtures.createStockEventPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class StockEventPostgresRepositoryIT(
    @Autowired private val repository: StockEventPostgresRepository
) : DatabaseIT(repository) {

    @Nested
    inner class SaveStockEventCases {

        @Test
        fun `should save stock event successfully`() {
            val model = createStockEventPostgresModel().also(repository::save)
            val savedModel = repository.findByIdOrNull(model.id)

            assertEquals(model, savedModel)
        }
    }
}
