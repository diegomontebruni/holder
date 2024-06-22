package com.montebruni.holder.transaction.infrastructure.database

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createTransaction
import com.montebruni.holder.transaction.infrastructure.database.postgres.TransactionPostgresRepository
import com.montebruni.holder.transaction.infrastructure.database.postgres.model.TransactionPostgresModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TransactionRepositoryAdapterTest(
    @MockK private val repository: TransactionPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: TransactionRepositoryAdapter

    @Test
    fun `should save a transaction successfully`() {
        val transaction = createTransaction()
        val modelSlot = slot<TransactionPostgresModel>()

        every { repository.save(capture(modelSlot)) } answers { modelSlot.captured }

        val result = adapter.save(transaction)

        val modelCaptured = modelSlot.captured
        assertEquals(transaction.walletId, modelCaptured.walletId)
        assertEquals(transaction.ticker, modelCaptured.ticker)
        assertEquals(transaction.quantity, modelCaptured.quantity)
        assertEquals(transaction.value.value.toDouble(), modelCaptured.value)
        assertEquals(transaction.operation.name, modelCaptured.operation)
        assertEquals(transaction.type.name, modelCaptured.type)
        assertEquals(transaction.description, modelCaptured.description)

        assertEquals(transaction, result)

        verify { repository.save(modelSlot.captured) }
    }
}
