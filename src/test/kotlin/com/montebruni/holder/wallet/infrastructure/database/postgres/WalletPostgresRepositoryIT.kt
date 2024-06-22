package com.montebruni.holder.wallet.infrastructure.database.postgres

import com.montebruni.holder.configuration.DatabaseIT
import com.montebruni.holder.fixtures.createWalletModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID
import kotlin.also

class WalletPostgresRepositoryIT(
    @Autowired val repository: WalletPostgresRepository
) : DatabaseIT(repository) {

    @Nested
    inner class SaveCases {

        @Test
        fun `should save wallet with diff updated at`() {
            val model = createWalletModel().also(repository::saveAndFlush)
            val updatedModel = model.copy(balance = 15.0).let(repository::saveAndFlush)

            assertNotEquals(model.updatedAt, updatedModel.updatedAt)
        }
    }

    @Nested
    inner class FindByManagerIdCases {

        @Test
        fun `should find wallet by manager id`() {
            val wallet = createWalletModel().also(repository::save)
            val result = repository.findByManagerId(wallet.managerId)

            assertEquals(wallet.id, result.first().id)
            assertEquals(wallet.managerId, result.first().managerId)
            assertEquals(wallet.balance, result.first().balance)
            assertEquals(wallet.createdAt, result.first().createdAt)
        }

        @Test
        fun `should return empty list when wallet not found`() {
            val result = repository.findByManagerId(UUID.randomUUID())

            assertTrue(result.isEmpty())
        }
    }
}
