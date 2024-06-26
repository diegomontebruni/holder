package com.montebruni.holder.wallet.application.usecase.impl

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCreateWalletInput
import com.montebruni.holder.wallet.application.client.UserClient
import com.montebruni.holder.wallet.application.client.exception.UserNotFoundException
import com.montebruni.holder.wallet.domain.entity.Wallet
import com.montebruni.holder.wallet.domain.repositories.WalletRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import java.util.UUID

class CreateWalletImplTest(
    @MockK private val walletRepository: WalletRepository,
    @MockK private val userClient: UserClient,
) : UnitTests() {

    @InjectMockKs
    private lateinit var createWallet: CreateWalletImpl

    @Test
    fun `should throw customer not found exception when customer not found`() {
        val input = createCreateWalletInput()

        every { userClient.findById(input.userId) } returns null

        assertThrows<UserNotFoundException> { createWallet.execute(input) }

        verify(exactly = 1) { userClient.findById(input.userId) }
        verify(exactly = 0) {
            walletRepository.create(any())
        }
    }

    @Test
    fun `should throw customer not found exception when manager is not null but not found`() {
        val input = createCreateWalletInput()
        val customerIdSlot = mutableListOf<UUID>()

        every { userClient.findById(capture(customerIdSlot)) } returns mockk() andThen null

        assertThrows<UserNotFoundException> { createWallet.execute(input) }

        customerIdSlot.forEach { verify { userClient.findById(it) } }
        verify(exactly = 0) {
            walletRepository.create(any())
        }
    }

    @Test
    fun `should create wallet successfully when manager id is the same of customer id`() {
        val input = createCreateWalletInput().copy(managerId = null)
        val walletSlot = slot<Wallet>()

        every { userClient.findById(input.userId) } returns mockk()
        every {
            walletRepository.create(capture(walletSlot))
        } answers { walletSlot.captured.copy(createdAt = Instant.now()) }

        createWallet.execute(input)

        val walletCaptured = walletSlot.captured
        assertEquals(input.userId, walletCaptured.id)
        assertEquals(input.userId, walletCaptured.managerId)
        assertNull(walletCaptured.createdAt)

        verify(exactly = 1) {
            userClient.findById(input.userId)
            walletRepository.create(walletSlot.captured)
        }
    }

    @Test
    fun `should create wallet successfully when manager id is different of customer id`() {
        val input = createCreateWalletInput()
        val findInputSlot = mutableListOf<UUID>()
        val walletSlot = slot<Wallet>()

        every { userClient.findById(capture(findInputSlot)) } returns mockk()
        every {
            walletRepository.create(capture(walletSlot))
        } answers { walletSlot.captured.copy(createdAt = Instant.now()) }

        createWallet.execute(input)

        val walletCaptured = walletSlot.captured
        assertEquals(input.userId, walletCaptured.id)
        assertEquals(input.managerId, walletCaptured.managerId)

        findInputSlot.forEach {
            verify { userClient.findById(it) }
            verify(exactly = 1) {
                walletRepository.create(walletSlot.captured)
            }
        }
    }
}
