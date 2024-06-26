package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.exception.CustomerNotFoundException
import com.montebruni.holder.account.domain.repositories.CustomerRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCustomer
import com.montebruni.holder.fixtures.createUpdateCustomerInput
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant

class UpdateCustomerImplTest(
    @MockK private val customerRepository: CustomerRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: UpdateCustomerImpl

    @Test
    fun `should throw exception when customer not found`() {
        val input = createUpdateCustomerInput()

        every { customerRepository.findById(input.id) } returns null

        assertThrows<CustomerNotFoundException> { usecase.execute(input) }

        verify(exactly = 1) { customerRepository.findById(input.id) }
        verify(exactly = 0) {
            customerRepository.save(any())
        }
    }

    @Test
    fun `should send event successfully when customer updated`() {
        val input = createUpdateCustomerInput()
        val customer = createCustomer().copy(id = input.id, createdAt = Instant.now())
        val repositoryInputSlot = slot<Customer>()

        every { customerRepository.findById(input.id) } returns customer
        every { customerRepository.save(capture(repositoryInputSlot)) } answers { repositoryInputSlot.captured }

        usecase.execute(input)

        val customerUpdated = repositoryInputSlot.captured
        assertEquals(input.id, customerUpdated.id)
        assertEquals(input.name, customerUpdated.name)

        verify(exactly = 1) {
            customerRepository.findById(input.id)
            customerRepository.save(repositoryInputSlot.captured)
        }
    }
}
