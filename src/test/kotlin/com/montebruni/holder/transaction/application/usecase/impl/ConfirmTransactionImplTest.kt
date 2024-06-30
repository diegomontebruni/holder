package com.montebruni.holder.transaction.application.usecase.impl

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createTransaction
import com.montebruni.holder.transaction.application.usecase.input.ConfirmTransactionInput
import com.montebruni.holder.transaction.domain.entity.Status
import com.montebruni.holder.transaction.domain.entity.Transaction
import com.montebruni.holder.transaction.domain.exception.TransactionNotFoundException
import com.montebruni.holder.transaction.domain.repositories.TransactionRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class ConfirmTransactionImplTest(
    @MockK private val transactionRepository: TransactionRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: ConfirmTransactionImpl

    private val input = ConfirmTransactionInput(transactionId = UUID.randomUUID())

    @Test
    fun `should throw error when transaction not found`() {
        every { transactionRepository.findById(input.transactionId) } returns null

        assertThrows<TransactionNotFoundException> { usecase.execute(input) }

        verify(exactly = 1) { transactionRepository.findById(input.transactionId) }
    }

    @Test
    fun `should confirm transaction`() {
        val transaction = createTransaction().copy(id = input.transactionId)
        val repositorySlot = slot<Transaction>()

        every { transactionRepository.findById(input.transactionId) } returns transaction
        every { transactionRepository.save(capture(repositorySlot)) } answers { repositorySlot.captured }

        usecase.execute(input)

        assertEquals(Status.CONFIRMED, repositorySlot.captured.status)

        verify(exactly = 1) {
            transactionRepository.findById(input.transactionId)
            transactionRepository.save(repositorySlot.captured)
        }
    }
}
