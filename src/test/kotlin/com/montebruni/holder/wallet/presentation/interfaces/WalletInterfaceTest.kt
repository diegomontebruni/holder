package com.montebruni.holder.wallet.presentation.interfaces

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createGetWalletByIdOutput
import com.montebruni.holder.wallet.application.usecase.GetWalletById
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID

class WalletInterfaceTest(
    @MockK private val getWalletById: GetWalletById
) : UnitTests() {

    @InjectMockKs
    private lateinit var walletInterface: WalletInterface

    @Test
    fun `should get wallet by id`() {
        val id = UUID.randomUUID()
        val walletOutput = createGetWalletByIdOutput()

        every { getWalletById.execute(id) } returns walletOutput

        val response = walletInterface.getWalletById(id)

        assertEquals(walletOutput.id, response?.id)
        assertEquals(walletOutput.managerId, response?.managerId)
        assertEquals(walletOutput.balance.value.toDouble(), response?.balance)
        assertEquals(walletOutput.createdAt, response?.createdAt)

        verify(exactly = 1) { getWalletById.execute(id) }
    }

    @Test
    fun `should return null when wallet not found by id`() {
        val id = UUID.randomUUID()

        every { getWalletById.execute(id) } returns null

        assertNull(walletInterface.getWalletById(id))

        verify(exactly = 1) { getWalletById.execute(id) }
    }
}
