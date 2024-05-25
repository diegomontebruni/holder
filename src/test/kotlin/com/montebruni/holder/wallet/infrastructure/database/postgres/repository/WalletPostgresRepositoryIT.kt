package com.montebruni.holder.wallet.infrastructure.database.postgres.repository

import com.montebruni.holder.configuration.DatabaseIT
import com.montebruni.holder.fixtures.createWalletModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

class WalletPostgresRepositoryIT(
    @Autowired val repository: WalletPostgresRepository
) : DatabaseIT(repository) {

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
