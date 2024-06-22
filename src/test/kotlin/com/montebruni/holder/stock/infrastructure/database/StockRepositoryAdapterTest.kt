package com.montebruni.holder.stock.infrastructure.database

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createStock
import com.montebruni.holder.fixtures.createStockPostgresModel
import com.montebruni.holder.stock.infrastructure.database.postgres.StockPostgresRepository
import com.montebruni.holder.stock.infrastructure.database.postgres.model.StockPostgresModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class StockRepositoryAdapterTest(
    @MockK private val repository: StockPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: StockRepositoryAdapter

    @Nested
    inner class SaveCases {

        @Test
        fun `should save stock and return it`() {
            val stock = createStock()
            val modelSlot = slot<StockPostgresModel>()

            every { repository.save(capture(modelSlot)) } answers { modelSlot.captured }

            val result = adapter.save(stock)

            val modelCaptured = modelSlot.captured
            assertEquals(stock.price.value.toDouble(), modelCaptured.price)
            assertEquals(stock.ticker, modelCaptured.ticker)
            assertNotNull(modelCaptured.createdAt)
            assertNotNull(modelCaptured.updatedAt)

            assertEquals(stock, result)

            verify { repository.save(modelSlot.captured) }
        }
    }

    @Nested
    inner class FindByTickerCases {

        @Test
        fun `should find stock by ticker`() {
            val ticker = "BBAS3"
            val model = createStockPostgresModel().copy(ticker = ticker)

            every { repository.findByIdOrNull(ticker) } returns model

            val result = adapter.findByTicker(ticker)

            assertEquals(result!!.price.value.toDouble(), model.price)
            assertEquals(result.ticker, model.ticker)

            verify { repository.findByIdOrNull(ticker) }
        }

        @Test
        fun `should return null when stock not found`() {
            val ticker = "BBAS3"

            every { repository.findByIdOrNull(ticker) } returns null

            val result = adapter.findByTicker(ticker)

            assertNull(result)

            verify { repository.findByIdOrNull(ticker) }
        }
    }
}
