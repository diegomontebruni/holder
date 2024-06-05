package com.montebruni.holder.wallet.application.usecase

import com.montebruni.holder.account.application.domain.exception.CustomerNotFoundException
import com.montebruni.holder.account.application.domain.port.CustomerRepository
import com.montebruni.holder.common.event.EventPublisher
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCreateWalletInput
import com.montebruni.holder.wallet.application.domain.entity.Wallet
import com.montebruni.holder.wallet.application.domain.events.WalletCreatedEvent
import com.montebruni.holder.wallet.application.domain.port.WalletRepository
import com.montebruni.holder.wallet.application.usecase.impl.CreateWalletImpl
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.util.UUID

class CreateWalletImplTest(
    @MockK private val walletRepository: WalletRepository,
    @MockK private val customerRepository: CustomerRepository,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var createWallet: CreateWalletImpl

    @Test
    fun `should throw customer not found exception when customer not found`() {
        val input = createCreateWalletInput()

        every { customerRepository.findById(input.customerId) } returns null

        assertThrows<CustomerNotFoundException> { createWallet.execute(input) }

        verify(exactly = 1) { customerRepository.findById(input.customerId) }
        verify(exactly = 0) {
            walletRepository.create(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should throw customer not found exception when manager is not null but not found`() {
        val input = createCreateWalletInput()
        val customerIdSlot = mutableListOf<UUID>()

        every { customerRepository.findById(capture(customerIdSlot)) } returns mockk() andThen null

        assertThrows<CustomerNotFoundException> { createWallet.execute(input) }

        customerIdSlot.forEach { verify { customerRepository.findById(it) } }
        verify(exactly = 0) {
            walletRepository.create(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should create wallet successfully when manager id is the same of customer id`() {
        val input = createCreateWalletInput().copy(managerId = null)
        val walletSlot = slot<Wallet>()
        val eventSlot = slot<WalletCreatedEvent>()

        every { customerRepository.findById(input.customerId) } returns mockk()
        every {
            walletRepository.create(capture(walletSlot))
        } answers { walletSlot.captured.copy(createdAt = Instant.now()) }
        justRun { eventPublisher.publishEvent(capture(eventSlot)) }

        val output = createWallet.execute(input)

        val walletCaptured = walletSlot.captured
        assertEquals(input.customerId, walletCaptured.id)
        assertEquals(input.customerId, walletCaptured.managerId)
        assertNull(walletCaptured.createdAt)

        val eventCaptured = eventSlot.captured.getData()
        assertEquals(walletSlot.captured.id, eventCaptured.id)
        assertEquals(walletSlot.captured.managerId, eventCaptured.managerId)
        assertEquals(walletSlot.captured.balance, eventCaptured.balance)
        assertNotNull(eventCaptured.createdAt)

        assertEquals(walletCaptured.id, output.id)
        assertEquals(walletCaptured.managerId, output.managerId)
        assertEquals(walletCaptured.balance, output.balance)
        assertEquals(eventCaptured.createdAt, output.createdAt)

        verify(exactly = 1) {
            customerRepository.findById(input.customerId)
            walletRepository.create(walletSlot.captured)
            eventPublisher.publishEvent(eventSlot.captured)
        }
    }

    @Test
    fun `should create wallet successfully when manager id is different of customer id`() {
        val input = createCreateWalletInput()
        val findInputSlot = mutableListOf<UUID>()
        val walletSlot = slot<Wallet>()
        val eventSlot = slot<WalletCreatedEvent>()

        every { customerRepository.findById(capture(findInputSlot)) } returns mockk()
        every {
            walletRepository.create(capture(walletSlot))
        } answers { walletSlot.captured.copy(createdAt = Instant.now()) }
        justRun { eventPublisher.publishEvent(capture(eventSlot)) }

        val output = createWallet.execute(input)

        val walletCaptured = walletSlot.captured
        assertEquals(input.customerId, walletCaptured.id)
        assertEquals(input.managerId, walletCaptured.managerId)

        val eventCaptured = eventSlot.captured.getData()
        assertEquals(input.customerId, eventCaptured.id)
        assertEquals(input.managerId, eventCaptured.managerId)

        assertEquals(input.customerId, output.id)
        assertEquals(input.managerId, output.managerId)

        findInputSlot.forEach {
            verify { customerRepository.findById(it) }
            verify(exactly = 1) {
                walletRepository.create(walletSlot.captured)
                eventPublisher.publishEvent(eventSlot.captured)
            }
        }
    }
}
