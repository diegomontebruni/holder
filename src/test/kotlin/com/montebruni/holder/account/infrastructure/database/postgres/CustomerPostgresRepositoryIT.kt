package com.montebruni.holder.account.infrastructure.database.postgres

import com.montebruni.holder.configuration.DatabaseIT
import com.montebruni.holder.fixtures.createCustomerModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID
import kotlin.also
import kotlin.let

class CustomerPostgresRepositoryIT(
    @Autowired val repository: CustomerPostgresRepository
) : DatabaseIT(repository) {

    @Nested
    inner class FindByIdCases {

        @Test
        fun `should find customer by id`() {
            val customer = createCustomerModel().also(repository::save)
            val result = repository.findByIdOrNull(customer.id)

            assertEquals(customer.id, result?.id)
            assertEquals(customer.userId, result?.userId)
            assertEquals(customer.name, result?.name)
            assertEquals(customer.email, result?.email)
            assertEquals(customer.createdAt, result?.createdAt)
        }

        @Test
        fun `should return null when customer not found`() {
            repository.findByIdOrNull(UUID.randomUUID()).let(::assertNull)
        }
    }

    @Nested
    inner class FindByUserIdCases {

        @Test
        fun `should find customer by user id`() {
            val customer = createCustomerModel().also(repository::save)
            val result = repository.findByUserId(customer.userId)

            assertEquals(customer.id, result?.id)
            assertEquals(customer.userId, result?.userId)
            assertEquals(customer.name, result?.name)
            assertEquals(customer.email, result?.email)
            assertEquals(customer.createdAt, result?.createdAt)
        }

        @Test
        fun `should return null when customer not found`() {
            repository.findByUserId(UUID.randomUUID()).let(::assertNull)
        }
    }

    @Nested
    inner class SaveCustomerCases {

        @Test
        fun `should save customer with nullable properties`() {
            val customer = createCustomerModel().copy(name = null).also(repository::save)

            val result = repository.findByIdOrNull(customer.id)

            assertNull(result?.name)
        }

        @Test
        fun `should save customer with diff updated at`() {
            val model = createCustomerModel().also(repository::saveAndFlush)
            val updatedModel = model.copy(name = "new name").let(repository::saveAndFlush)

            assertNotEquals(model.updatedAt, updatedModel.updatedAt)
        }
    }
}
