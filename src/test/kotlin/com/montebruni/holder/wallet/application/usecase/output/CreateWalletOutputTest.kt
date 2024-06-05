package com.montebruni.holder.wallet.application.usecase.output

import com.montebruni.holder.fixtures.createWallet
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.time.Instant

class CreateWalletOutputTest {

    @Test
    fun `should create output from wallet`() {
        val wallet = createWallet().copy(createdAt = Instant.now())
        val output = CreateWalletOutput.fromWallet(wallet)

        assertEquals(wallet.id, output.id)
        assertEquals(wallet.managerId, output.managerId)
        assertEquals(wallet.balance, output.balance)
        assertEquals(wallet.createdAt, output.createdAt)
    }

    @Test
    fun `should create output from wallet with null creation date`() {
        val wallet = createWallet()

        assertThrows<IllegalArgumentException> { CreateWalletOutput.fromWallet(wallet) }
    }
}
