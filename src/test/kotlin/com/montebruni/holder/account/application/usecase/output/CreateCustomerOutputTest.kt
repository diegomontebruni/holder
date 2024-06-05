package com.montebruni.holder.account.application.usecase.output

import com.montebruni.holder.fixtures.createCustomer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.time.Instant

class CreateCustomerOutputTest {

    @Test
    fun `should create a CreateCustomerOutput from a Customer`() {
        val customer = createCustomer().copy(createdAt = Instant.now())
        val output = CreateCustomerOutput.fromCustomer(customer)

        assertEquals(customer.id, output.id)
        assertEquals(customer.userId, output.userId)
        assertEquals(customer.email, output.email)
        assertEquals(customer.createdAt, output.createdAt)
    }

    @Test
    fun `should throw an exception when trying to create a output from a Customer without a creation date`() {
        val customer = createCustomer().copy(createdAt = null)

        assertThrows<IllegalArgumentException> { CreateCustomerOutput.fromCustomer(customer) }
    }
}
