package com.montebruni.holder.wallet.application.usecase.input

import com.montebruni.holder.fixtures.createCreateWalletInput
import com.montebruni.holder.fixtures.createCustomerCreatedEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CreateWalletInputTest {

    @Nested
    inner class FromCustomerCreatedEventCases {

        @Test
        fun `should create input from customer created event`() {
            val event = createCustomerCreatedEvent()
            val input = CreateWalletInput.fromEvent(event)

            assertEquals(event.getData().id, input.customerId)
            assertEquals(event.getData().managerId, input.managerId)
        }

        @Test
        fun `should create input from customer created event with null manager id`() {
            val event = createCustomerCreatedEvent().copy(managerId = null)
            val input = CreateWalletInput.fromEvent(event)

            assertEquals(event.getData().id, input.customerId)
            assertNull(input.managerId)
        }
    }

    @Nested
    inner class ToWalletCases {

        @Test
        fun `should create wallet from input when manager id is null`() {
            val input = createCreateWalletInput().copy(managerId = null)
            val wallet = input.toWallet()

            assertEquals(input.customerId, wallet.id)
            assertEquals(input.customerId, wallet.managerId)
            assertEquals(BigDecimal.ZERO, wallet.balance.value)
            assertNull(wallet.createdAt)
        }

        @Test
        fun `should create wallet from input when manager id is not null`() {
            val input = createCreateWalletInput()
            val wallet = input.toWallet()

            assertEquals(input.customerId, wallet.id)
            assertEquals(input.managerId, wallet.managerId)
            assertEquals(BigDecimal.ZERO, wallet.balance.value)
            assertNull(wallet.createdAt)
        }
    }
}
