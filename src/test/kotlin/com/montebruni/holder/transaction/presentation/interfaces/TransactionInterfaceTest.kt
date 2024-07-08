package com.montebruni.holder.transaction.presentation.interfaces

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createFindTransactionByIdOutput
import com.montebruni.holder.transaction.application.usecase.FindById
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID

class TransactionInterfaceTest(
    @MockK private val findById: FindById
) : UnitTests() {

    @InjectMockKs
    private lateinit var transactionInterface: TransactionInterface

    @Test
    fun `should find transaction by id`() {
        val id = UUID.randomUUID()
        val findByIdOutput = createFindTransactionByIdOutput().copy(id = id)

        every { findById.execute(id) } returns findByIdOutput

        val response = transactionInterface.findById(id)

        assertNotNull(response)
        assertEquals(id, response?.id)
        assertEquals(findByIdOutput.status.name, response?.status)
        assertEquals(findByIdOutput.walletId, response?.walletId)
        assertEquals(findByIdOutput.ticker, response?.ticker)
        assertEquals(findByIdOutput.quantity, response?.quantity)
        assertEquals(findByIdOutput.value.value.toDouble(), response?.value)
        assertEquals(findByIdOutput.operation.name, response?.operation)
        assertEquals(findByIdOutput.type.name, response?.type)
        assertEquals(findByIdOutput.description, response?.description)
        assertEquals(findByIdOutput.createdAt, response?.createdAt)

        verify(exactly = 1) { findById.execute(id) }
    }

    @Test
    fun `should return null when transaction not found by id`() {
        val id = UUID.randomUUID()

        every { findById.execute(id) } returns null

        assertNull(transactionInterface.findById(id))

        verify(exactly = 1) { findById.execute(id) }
    }
}
