package com.montebruni.holder.transaction.infrastructure.database

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createTransaction
import com.montebruni.holder.fixtures.createTransactionPostgresModel
import com.montebruni.holder.transaction.infrastructure.database.postgres.TransactionPostgresRepository
import com.montebruni.holder.transaction.infrastructure.database.postgres.model.TransactionPostgresModel
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
import java.util.UUID.randomUUID

class TransactionRepositoryAdapterTest(
    @MockK private val repository: TransactionPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: TransactionRepositoryAdapter

    @Nested
    inner class SaveCases {
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

            assertEquals(transaction.walletId, result.walletId)
            assertEquals(transaction.ticker, result.ticker)
            assertEquals(transaction.quantity, result.quantity)
            assertEquals(transaction.value, result.value)
            assertEquals(transaction.operation, result.operation)
            assertEquals(transaction.type, result.type)
            assertEquals(transaction.description, result.description)
            assertNotNull(result.id)
            assertNotNull(result.createdAt)

            verify { repository.save(modelSlot.captured) }
        }
    }

    @Nested
    inner class FindByIdCases {

        @Test
        fun `should return null when transaction not found`() {
            val id = randomUUID()
            every { repository.findByIdOrNull(id) } returns null

            assertNull(adapter.findById(id))

            verify { repository.findByIdOrNull(id) }
        }

        @Test
        fun `should return a transaction when found`() {
            val id = randomUUID()
            val transactionModel = createTransactionPostgresModel().copy(id = id)

            every { repository.findByIdOrNull(id) } returns transactionModel

            val result = adapter.findById(id)

            assertEquals(transactionModel.walletId, result?.walletId)
            assertEquals(transactionModel.ticker, result?.ticker)
            assertEquals(transactionModel.quantity, result?.quantity)
            assertEquals(transactionModel.value, result?.value?.value?.toDouble())
            assertEquals(transactionModel.operation, result?.operation?.name)
            assertEquals(transactionModel.type, result?.type?.name)
            assertEquals(transactionModel.description, result?.description)
            assertEquals(transactionModel.id, result?.id)
            assertEquals(transactionModel.createdAt, result?.createdAt)

            verify { repository.findByIdOrNull(id) }
        }
    }
}
