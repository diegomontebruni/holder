package com.montebruni.holder.account.application.usecase.input

import com.montebruni.holder.fixtures.createCreateCustomerInput
import com.montebruni.holder.fixtures.createUserCreatedEvent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CreateCustomerInputTest {

    @Nested
    inner class FromUserCreatedEventCases {

        @Test
        fun `should create input from user created event`() {
            val event = createUserCreatedEvent()
            val input = CreateCustomerInput.fromEvent(event)

            assertEquals(event.getData().id, input.userId)
            assertEquals(event.getData().username?.value, input.email.value)
        }
    }

    @Nested
    inner class ToCustomerCases {

        @Test
        fun `should create customer from input`() {
            val input = createCreateCustomerInput()
            val customer = input.toCustomer()

            assertEquals(input.userId, customer.userId)
            assertEquals(input.email, customer.email)
        }
    }
}
