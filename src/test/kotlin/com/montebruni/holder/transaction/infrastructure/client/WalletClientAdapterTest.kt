package com.montebruni.holder.transaction.infrastructure.client

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.wallet.presentation.interfaces.WalletInterface
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
    @MockK private val walletInterface: WalletInterface
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: WalletClientAdapter

    private val walletId = UUID.randomUUID()

    @Test
    fun `should return true when wallet exists`() {
        every { walletInterface.getWalletById(walletId) } returns mockk()

        assertTrue(adapter.existsByWalletId(walletId))
        verify(exactly = 1) { walletInterface.getWalletById(walletId) }
    }

    @Test
    fun `should return false when wallet does not exist`() {
        every { walletInterface.getWalletById(walletId) } returns null

        assertFalse(adapter.existsByWalletId(walletId))
        verify(exactly = 1) { walletInterface.getWalletById(walletId) }
    }
}
