package com.montebruni.holder.asset.infrastructure.client

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createFindTransactionByIdResponse
import com.montebruni.holder.transaction.presentation.interfaces.TransactionInterface
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID.randomUUID

class TransactionClientAdapterTest(
    @MockK private val transactionInterface: TransactionInterface
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: TransactionClientAdapter

    @Test
    fun `should return null when transaction not found`() {
        val id = randomUUID()

        every { transactionInterface.findById(id) } returns null

        assertNull(adapter.findById(id))

        verify { transactionInterface.findById(id) }
    }

    @Test
    fun `should return transaction response when transaction found`() {
        val id = randomUUID()
        val transaction = createFindTransactionByIdResponse().copy(id = id)

        every { transactionInterface.findById(id) } returns transaction

        val response = adapter.findById(id)

        assertEquals(transaction.status, response?.status?.name)

        verify { transactionInterface.findById(id) }
    }
}
