package com.montebruni.holder.transaction.infrastructure.database.postgres.model

import com.montebruni.holder.fixtures.createTransaction
import com.montebruni.holder.fixtures.createTransactionPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class TransactionPostgresModelTest {

    @Test
    fun `should convert from Transaction to model`() {
        val transaction = createTransaction()
        val model = TransactionPostgresModel.fromTransaction(transaction)

        assertEquals(transaction.id, model.id)
        assertEquals(transaction.walletId, model.walletId)
        assertEquals(transaction.ticker, model.ticker)
        assertEquals(transaction.quantity, model.quantity)
        assertEquals(transaction.value.value.toDouble(), model.value)
        assertEquals(transaction.operation.name, model.operation)
        assertEquals(transaction.type.name, model.type)
        assertEquals(transaction.description, model.description)
        assertNotNull(model.createdAt)
    }

    @Test
    fun `should convert from model to Transaction`() {
        val model = createTransactionPostgresModel()
        val transaction = model.toTransaction()

        assertEquals(model.id, transaction.id)
        assertEquals(model.status, transaction.status.name)
        assertEquals(model.walletId, transaction.walletId)
        assertEquals(model.ticker, transaction.ticker)
        assertEquals(model.quantity, transaction.quantity)
        assertEquals(model.value, transaction.value.value.toDouble())
        assertEquals(model.operation, transaction.operation.name)
        assertEquals(model.type, transaction.type.name)
        assertEquals(model.description, transaction.description)
        assertNotNull(transaction.createdAt)
    }
}
