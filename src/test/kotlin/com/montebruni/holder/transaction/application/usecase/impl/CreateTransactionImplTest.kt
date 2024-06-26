package com.montebruni.holder.transaction.application.usecase.impl

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCreateTransactionInput
import com.montebruni.holder.transaction.application.client.StockClient
import com.montebruni.holder.transaction.application.client.WalletClient
import com.montebruni.holder.transaction.application.client.exception.StockNotFoundException
import com.montebruni.holder.transaction.application.client.exception.WalletNotFoundException
import com.montebruni.holder.transaction.application.event.EventPublisher
import com.montebruni.holder.transaction.application.event.events.TransactionCreatedEvent
import com.montebruni.holder.transaction.application.usecase.input.toTransaction
import com.montebruni.holder.transaction.domain.entity.Transaction
import com.montebruni.holder.transaction.domain.repositories.TransactionRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant

class CreateTransactionImplTest(
    @MockK private val transactionRepository: TransactionRepository,
    @MockK private val walletClient: WalletClient,
    @MockK private val stockClient: StockClient,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: CreateTransactionImpl

    private val input = createCreateTransactionInput()

    @Test
    fun `should throw error when wallet not exists`() {
        every { walletClient.existsByWalletId(input.walletId) } returns false

        assertThrows<WalletNotFoundException> {
            usecase.execute(input)
        }

        verify(exactly = 1) { walletClient.existsByWalletId(input.walletId) }
        verify(exactly = 0) {
            stockClient.existsByTicker(any())
            transactionRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should throw error when stock not exists`() {
        every { walletClient.existsByWalletId(input.walletId) } returns true
        every { stockClient.existsByTicker(input.ticker) } returns false

        assertThrows<StockNotFoundException> {
            usecase.execute(input)
        }

        verify(exactly = 1) {
            walletClient.existsByWalletId(input.walletId)
            stockClient.existsByTicker(input.ticker)
        }
        verify(exactly = 0) {
            transactionRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should create transaction successfully`() {
        val transaction = input.toTransaction().copy(createdAt = Instant.now())
        val transactionSlot = slot<Transaction>()
        val eventSlot = slot<TransactionCreatedEvent>()

        every { walletClient.existsByWalletId(input.walletId) } returns true
        every { stockClient.existsByTicker(input.ticker) } returns true
        every { transactionRepository.save(capture(transactionSlot)) } returns transaction
        justRun { eventPublisher.publishEvent(capture(eventSlot)) }

        usecase.execute(input)

        val transactionCaptured = transactionSlot.captured
        assertEquals(input.walletId, transactionCaptured.walletId)
        assertEquals(input.ticker, transactionCaptured.ticker)
        assertEquals(input.quantity, transactionCaptured.quantity)
        assertEquals(input.value, transactionCaptured.value)
        assertEquals(input.operation, transactionCaptured.operation)
        assertEquals(input.type, transactionCaptured.type)
        assertEquals(input.description, transactionCaptured.description)

        val eventData = eventSlot.captured.getData()
        assertEquals(transaction.id, eventData.id)
        assertEquals(transaction.walletId, eventData.walletId)
        assertEquals(transaction.ticker, eventData.ticker)
        assertEquals(transaction.quantity, eventData.quantity)
        assertEquals(transaction.value, eventData.value)
        assertEquals(transaction.operation, eventData.operation)
        assertEquals(transaction.type, eventData.type)
        assertEquals(transaction.description, eventData.description)
        assertEquals(transaction.createdAt, eventData.createdAt)

        verify(exactly = 1) {
            walletClient.existsByWalletId(input.walletId)
            stockClient.existsByTicker(input.ticker)
            transactionRepository.save(transactionSlot.captured)
            eventPublisher.publishEvent(eventSlot.captured)
        }
    }
}
