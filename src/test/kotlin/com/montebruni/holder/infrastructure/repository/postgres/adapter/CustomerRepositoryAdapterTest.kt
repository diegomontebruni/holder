package com.montebruni.holder.infrastructure.repository.postgres.adapter

import com.montebruni.holder.account.infrastructure.database.postgres.adapter.CustomerRepositoryAdapter
import com.montebruni.holder.account.infrastructure.database.postgres.repository.CustomerPostgresRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCustomerModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
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
            assertEquals(customerModel.email, result?.email)
            assertEquals(customerModel.createdAt, result?.createdAt)
        }

        @Test
        fun `should return null when not found customer by id`() {
            val customerModel = createCustomerModel()

            every { repository.findByIdOrNull(customerModel.id) } returns null

            adapter.findById(customerModel.id).let(::assertNull)
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
            assertEquals(customerModel.email, result?.email)
            assertEquals(customerModel.createdAt, result?.createdAt)
        }

        @Test
        fun `should return null when not found customer by user id`() {
            val customerModel = createCustomerModel()

            every { repository.findByUserId(customerModel.userId) } returns null

            adapter.findByUserId(customerModel.userId).let(::assertNull)
        }
    }
}
