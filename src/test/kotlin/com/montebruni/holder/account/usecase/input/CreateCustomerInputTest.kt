package com.montebruni.holder.account.usecase.input

import com.montebruni.holder.fixtures.createCreateCustomerInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateCustomerInputTest {

    @Test
    fun `should create customer from input`() {
        val input = createCreateCustomerInput()
        val customer = input.toCustomer()

        assertEquals(input.userId, customer.userId)
        assertEquals(input.email, customer.email)
    }
}
