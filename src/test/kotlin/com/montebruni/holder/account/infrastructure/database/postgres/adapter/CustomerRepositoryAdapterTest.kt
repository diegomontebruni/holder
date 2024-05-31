package com.montebruni.holder.account.infrastructure.database.postgres.adapter

import com.montebruni.holder.account.infrastructure.database.postgres.model.CustomerPostgresModel
import com.montebruni.holder.account.infrastructure.database.postgres.repository.CustomerPostgresRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCustomer
import com.montebruni.holder.fixtures.createCustomerModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class CustomerRepositoryAdapterTest(
    @MockK private val repository: CustomerPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: CustomerRepositoryAdapter

    @Nested
    inner class FindByIdCases {

        @Test
        fun `should find customer by id`() {
            val customerModel = createCustomerModel()

            every { repository.findByIdOrNull(customerModel.id) } returns customerModel

            val result = adapter.findById(customerModel.id)

            assertNotNull(result)
            assertEquals(customerModel.id, result?.id)
            assertEquals(customerModel.userId, result?.userId)
            assertEquals(customerModel.name, result?.name)
            assertEquals(customerModel.email, result?.email?.value)
            assertEquals(customerModel.createdAt, result?.createdAt)

            verify { repository.findByIdOrNull(customerModel.id) }
        }

        @Test
        fun `should return null when not found customer by id`() {
            val customerModel = createCustomerModel()

            every { repository.findByIdOrNull(customerModel.id) } returns null

            adapter.findById(customerModel.id).let(::assertNull)

            verify { repository.findByIdOrNull(customerModel.id) }
        }
    }

    @Nested
    inner class FindByUserIdCases {

        @Test
        fun `should find customer by user id`() {
            val customerModel = createCustomerModel()

            every { repository.findByUserId(customerModel.userId) } returns customerModel

            val result = adapter.findByUserId(customerModel.userId)

            assertNotNull(result)
            assertEquals(customerModel.id, result?.id)
            assertEquals(customerModel.userId, result?.userId)
            assertEquals(customerModel.name, result?.name)
            assertEquals(customerModel.email, result?.email?.value)
            assertEquals(customerModel.createdAt, result?.createdAt)

            verify { repository.findByUserId(customerModel.userId) }
        }

        @Test
        fun `should return null when not found customer by user id`() {
            val customerModel = createCustomerModel()

            every { repository.findByUserId(customerModel.userId) } returns null

            adapter.findByUserId(customerModel.userId).let(::assertNull)

            verify { repository.findByUserId(customerModel.userId) }
        }
    }

    @Nested
    inner class SaveCustomerCases {

        @Test
        fun `should save a customer successfully`() {
            val customer = createCustomer()
            val modelSlot = slot<CustomerPostgresModel>()

            every { repository.save(capture(modelSlot)) } answers { modelSlot.captured }

            val result = adapter.save(customer)

            val customerModel = modelSlot.captured
            assertEquals(customer.id, customerModel.id)
            assertEquals(customer.userId, customerModel.userId)
            assertEquals(customer.name, customerModel.name)
            assertEquals(customer.email.value, customerModel.email)
            assertNotNull(customerModel.createdAt)

            assertEquals(customer.id, result.id)
            assertEquals(customer.userId, result.userId)
            assertEquals(customer.name, result.name)
            assertEquals(customer.email.value, result.email.value)
            assertNotNull(result.createdAt)

            verify { repository.save(modelSlot.captured) }
        }

        @Test
        fun `should save customer with nullable properties`() {
            val customer = createCustomer().copy(name = null)

            every { repository.save(any()) } answers { firstArg() }

            val result = adapter.save(customer)

            assertNull(result.name)

            verify { repository.save(any()) }
        }
    }
}
