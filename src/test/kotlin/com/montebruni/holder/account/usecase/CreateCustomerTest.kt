package com.montebruni.holder.account.usecase

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.events.data.CustomerCreatedEvent
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.port.CustomerRepository
import com.montebruni.holder.account.domain.port.UserRepository
import com.montebruni.holder.common.event.EventPublisher
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCreateCustomerInput
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant

class CreateCustomerTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val customerRepository: CustomerRepository,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var createCustomer: CreateCustomer

    private val input = createCreateCustomerInput()

    @Test
    fun `should throw user not found exception when user does not exist`() {
        every { userRepository.findById(input.userId) } returns null

        assertThrows<UserNotFoundException> { createCustomer.execute(input) }

        verify { userRepository.findById(input.userId) }
        verify(exactly = 0) {
            customerRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should create a customer successfully`() {
        val customerSlot = slot<Customer>()
        val eventSlot = slot<CustomerCreatedEvent>()

        every { userRepository.findById(input.userId) } returns mockk()
        every {
            customerRepository.save(capture(customerSlot))
        } answers {
            customerSlot.captured.copy(createdAt = Instant.now())
        }
        justRun { eventPublisher.publishEvent(capture(eventSlot)) }

        val output = createCustomer.execute(input)

        val customerCaptured = customerSlot.captured
        assertEquals(input.userId, customerCaptured.userId)
        assertEquals(input.email, customerCaptured.email)
        assertNull(customerCaptured.name)
        assertNull(customerCaptured.createdAt)

        assertEquals(customerCaptured.id, output.id)
        assertEquals(customerCaptured.userId, output.userId)
        assertEquals(customerCaptured.email, output.email)
        assertNotNull(output.createdAt)

        val eventCaptured = eventSlot.captured.getData()
        assertEquals(customerCaptured.id, eventCaptured.id)
        assertEquals(customerCaptured.userId, eventCaptured.userId)
        assertEquals(customerCaptured.email, eventCaptured.email)
        assertEquals(input.managerId, eventCaptured.managerId)
        assertEquals(eventCaptured.createdAt, output.createdAt)
        assertNull(eventCaptured.name)

        verify {
            userRepository.findById(input.userId)
            customerRepository.save(customerSlot.captured)
            eventPublisher.publishEvent(eventSlot.captured)
        }
    }
}
