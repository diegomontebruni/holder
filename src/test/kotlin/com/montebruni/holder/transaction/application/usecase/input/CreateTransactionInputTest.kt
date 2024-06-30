package com.montebruni.holder.transaction.application.usecase.input

import com.montebruni.holder.fixtures.createCreateTransactionInput
import com.montebruni.holder.transaction.domain.entity.Status
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CreateTransactionInputTest {

    @Test
    fun `should convert input to transaction`() {
        val input = createCreateTransactionInput()
        val transaction = input.toTransaction()

        assertEquals(input.walletId, transaction.walletId)
        assertEquals(input.ticker, transaction.ticker)
        assertEquals(input.quantity, transaction.quantity)
        assertEquals(input.value, transaction.value)
        assertEquals(input.operation, transaction.operation)
        assertEquals(input.type, transaction.type)
        assertEquals(input.description, transaction.description)
        assertEquals(Status.PENDING, transaction.status)
        assertNull(transaction.createdAt)
    }
}
