package com.montebruni.holder.transaction.application.usecase.impl

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

class FindTransactionByIdImplTest(
    @MockK private val transactionRepository: TransactionRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: FindTransactionByIdImpl

    @Test
    fun `should find transaction by id`() {
        val id = randomUUID()
        val transaction = createTransaction()

        every { transactionRepository.findById(id) } returns transaction

        val output = usecase.execute(id)

        assertEquals(transaction.id, output?.id)
        assertEquals(transaction.status, output?.status)
        assertEquals(transaction.walletId, output?.walletId)
        assertEquals(transaction.ticker, output?.ticker)
        assertEquals(transaction.quantity, output?.quantity)
        assertEquals(transaction.value, output?.value)
        assertEquals(transaction.operation, output?.operation)
        assertEquals(transaction.type, output?.type)
        assertEquals(transaction.description, output?.description)
        assertEquals(transaction.createdAt, output?.createdAt)

        verify(exactly = 1) { transactionRepository.findById(id) }
    }

    @Test
    fun `should return null when transaction not found`() {
        val id = randomUUID()

        every { transactionRepository.findById(id) } returns null

        assertNull(usecase.execute(id))

        verify(exactly = 1) { transactionRepository.findById(id) }
    }
}
