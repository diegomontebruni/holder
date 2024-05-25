package com.montebruni.holder.wallet.infrastructure.database.postgres.model

import com.montebruni.holder.fixtures.createWallet
import com.montebruni.holder.fixtures.createWalletModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class WalletPostgresModelTest {

    @Nested
    inner class FromWalletCases {

        @Test
        fun `should create model from wallet`() {
            val wallet = createWallet()
            val model = WalletPostgresModel.fromWallet(wallet)

            assertEquals(wallet.id, model.id)
            assertEquals(wallet.managerId, model.managerId)
            assertEquals(wallet.balance.value.toDouble(), model.balance)
        }
    }

    @Nested
    inner class ToWalletCases {

        @Test
        fun `should create wallet from model`() {
            val model = createWalletModel()
            val wallet = model.toWallet()

            assertEquals(model.id, wallet.id)
            assertEquals(model.managerId, wallet.managerId)
            assertEquals(model.balance, wallet.balance.value.toDouble())
        }
    }
}
