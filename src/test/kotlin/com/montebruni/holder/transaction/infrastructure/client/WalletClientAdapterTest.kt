package com.montebruni.holder.transaction.infrastructure.client

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.wallet.domain.repositories.WalletRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.UUID

class WalletClientAdapterTest(
    @MockK private val walletRepository: WalletRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: WalletClientAdapter

    private val walletId = UUID.randomUUID()

    @Test
    fun `should return true when wallet exists`() {
        every { walletRepository.findById(walletId) } returns mockk()

        assertTrue(adapter.existsByWalletId(walletId))
        verify(exactly = 1) { walletRepository.findById(walletId) }
    }

    @Test
    fun `should return false when wallet does not exist`() {
        every { walletRepository.findById(walletId) } returns null

        assertFalse(adapter.existsByWalletId(walletId))
        verify(exactly = 1) { walletRepository.findById(walletId) }
    }
}
