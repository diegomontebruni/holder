package com.montebruni.holder.stock.infrastructure.database

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createStockEvent
import com.montebruni.holder.stock.infrastructure.database.postgres.StockEventPostgresRepository
import com.montebruni.holder.stock.infrastructure.database.postgres.model.StockEventPostgresModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class StockEventRepositoryAdapterTest(
    @MockK private val repository: StockEventPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: StockEventRepositoryAdapter

    @Test
    fun `should adapt the save of stock event successfully`() {
        val stockEvent = createStockEvent()
        val modelSlot = slot<StockEventPostgresModel>()

        every { repository.save(capture(modelSlot)) } answers { modelSlot.captured }

        val result = adapter.save(stockEvent)

        val modelCaptured = modelSlot.captured
        assertEquals(stockEvent.amount.value.toDouble(), modelCaptured.amount)
        assertEquals(stockEvent.symbol, modelCaptured.symbol)
        assertEquals(stockEvent.type.name, modelCaptured.type)
        assertEquals(stockEvent.description, modelCaptured.description)
        assertEquals(stockEvent.paymentDate, modelCaptured.paymentDate)
        assertEquals(stockEvent.approvedAt, modelCaptured.approvedAt)
        assertNotNull(modelCaptured.id)
        assertNotNull(modelCaptured.createdAt)

        assertEquals(stockEvent, result)

        verify { repository.save(modelSlot.captured) }
    }
}
