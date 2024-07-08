package com.montebruni.holder.wallet.application.usecase.impl

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createWallet
import com.montebruni.holder.wallet.domain.repositories.WalletRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.time.Instant
import java.util.UUID

class GetWalletByIdImplTest(
    @MockK private val walletRepository: WalletRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: GetWalletByIdImpl

    @Test
    fun `should get wallet by id`() {
        val walletId = UUID.randomUUID()
        val wallet = createWallet().copy(id = walletId, createdAt = Instant.now())

        every { walletRepository.findById(walletId) } returns wallet

        val output = usecase.execute(walletId)

        assertEquals(wallet.id, output?.id)
        assertEquals(wallet.managerId, output?.managerId)
        assertEquals(wallet.balance, output?.balance)
        assertEquals(wallet.createdAt, output?.createdAt)

        verify { walletRepository.findById(walletId) }
    }

    @Test
    fun `should return null when wallet not found by id`() {
        val walletId = UUID.randomUUID()

        every { walletRepository.findById(walletId) } returns null

        assertNull(usecase.execute(walletId))

        verify { walletRepository.findById(walletId) }
    }

    @Test
    fun `should throw exception when created at is null`() {
        val walletId = UUID.randomUUID()
        val wallet = createWallet().copy(id = walletId)

        every { walletRepository.findById(walletId) } returns wallet

        assertThrows<IllegalArgumentException> { usecase.execute(walletId) }

        verify { walletRepository.findById(walletId) }
    }
}
