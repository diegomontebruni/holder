package com.montebruni.holder.asset.infrastructure.client

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createTransaction
import com.montebruni.holder.transaction.domain.repositories.TransactionRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID.randomUUID

class TransactionClientAdapterTest(
    @MockK private val transactionRepository: TransactionRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: TransactionClientAdapter

    @Test
    fun `should return null when transaction not found`() {
        val id = randomUUID()

        every { transactionRepository.findById(id) } returns null

        assertNull(adapter.findById(id))

        verify { transactionRepository.findById(id) }
    }

    @Test
    fun `should return transaction response when transaction found`() {
        val id = randomUUID()
        val transaction = createTransaction()

        every { transactionRepository.findById(id) } returns transaction

        val response = adapter.findById(id)

        assertEquals(transaction.status.name, response?.status?.name)

        verify { transactionRepository.findById(id) }
    }
}
