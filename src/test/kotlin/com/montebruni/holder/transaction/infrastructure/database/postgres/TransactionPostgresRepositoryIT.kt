package com.montebruni.holder.transaction.infrastructure.database.postgres

import com.montebruni.holder.configuration.DatabaseIT
import com.montebruni.holder.fixtures.createTransactionPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.repository.findByIdOrNull

class TransactionPostgresRepositoryIT(
    @Autowired private val repository: TransactionPostgresRepository
) : DatabaseIT(repository) {

    @Test
    fun `should save a transaction`() {
        val model = createTransactionPostgresModel().also(repository::save)

        val savedTransaction = repository.findByIdOrNull(model.id)

        assertNotNull(savedTransaction)
        assertEquals(model, savedTransaction)
    }

    @Test
    fun `should throw duplicated key exception when try to save a transaction with same id`() {
        val model = createTransactionPostgresModel().also(repository::saveAndFlush)

        assertThrows<DuplicateKeyException> {
            model.copy(
                ticker = "PETR4",
                quantity = 10,
                value = 30.0,
                operation = "BUY",
                type = "STOCK",
                description = "Description"
            ).let(repository::saveAndFlush)
        }
    }
}
