package com.montebruni.holder.account.infrastructure.database.postgres.model

import com.montebruni.holder.fixtures.createCustomerModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CustomerPostgresModelTest {

    @Test
    fun `should create customer from model`() {
        val model = createCustomerModel()
        val customer = model.toCustomer()

        assertEquals(model.id, customer.id)
        assertEquals(model.userId, customer.userId)
        assertEquals(model.name, customer.name)
        assertEquals(model.email, customer.email)
        assertEquals(model.createdAt, customer.createdAt)
    }
}
