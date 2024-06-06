package com.montebruni.holder.wallet.infrastructure.client

import com.montebruni.holder.account.domain.repositories.CustomerRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCustomer
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID

class CustomerClientAdapterTest(
    @MockK private val customerRepository: CustomerRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var customerClientAdapter: CustomerClientAdapter

    @Test
    fun `should find customer by id`() {
        val customerId = UUID.randomUUID()
        val customer = createCustomer().copy(id = customerId)

        every { customerRepository.findById(customerId) } returns customer

        val result = customerClientAdapter.findById(customerId)

        assertNotNull(result)
        assertEquals(customerId, result?.id)

        verify(exactly = 1) { customerRepository.findById(customerId) }
    }

    @Test
    fun `should return null when customer not found`() {
        val customerId = UUID.randomUUID()

        every { customerRepository.findById(customerId) } returns null

        val result = customerClientAdapter.findById(customerId)

        assertNull(result)

        verify(exactly = 1) { customerRepository.findById(customerId) }
    }
}
