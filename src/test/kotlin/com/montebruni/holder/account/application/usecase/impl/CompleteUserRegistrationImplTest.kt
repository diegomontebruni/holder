package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.UserRegistrationCompletedEvent
import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.exception.CustomerNotFoundException
import com.montebruni.holder.account.domain.exception.UserAlreadyRegisteredException
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.CustomerRepository
import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCompleteUserRegistrationInput
import com.montebruni.holder.fixtures.createCustomer
import com.montebruni.holder.fixtures.createUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CompleteUserRegistrationImplTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val customerRepository: CustomerRepository,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: CompleteUserRegistrationImpl

    @Test
    fun `should throw user already registered when user is already registered`() {
        val input = createCompleteUserRegistrationInput()
        val user = createUser().copy(status = Status.ACTIVE)

        every { userRepository.findById(input.userId) } returns user

        assertThrows<UserAlreadyRegisteredException> { usecase.execute(input) }

        verify { userRepository.findById(input.userId) }
        verify(exactly = 0) {
            customerRepository.findByUserId(any())
            userRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should throw user not found exception when user not exists`() {
        val input = createCompleteUserRegistrationInput()

        every { userRepository.findById(input.userId) } returns null

        assertThrows<UserNotFoundException> { usecase.execute(input) }

        verify { userRepository.findById(input.userId) }
        verify(exactly = 0) {
            customerRepository.findByUserId(any())
            userRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should throw customer not found exception when customer no exists`() {
        val input = createCompleteUserRegistrationInput()
        val user = createUser()

        every { userRepository.findById(input.userId) } returns user
        every { customerRepository.findByUserId(user.id) } returns null

        assertThrows<CustomerNotFoundException> { usecase.execute(input) }

        verify { userRepository.findById(input.userId) }
        verify { customerRepository.findByUserId(user.id) }
        verify(exactly = 0) {
            userRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should send user registration completed event when user is registered successfully`() {
        val input = createCompleteUserRegistrationInput()
        val user = createUser().copy(id = input.userId)
        val customer = createCustomer().copy(userId = input.userId)

        val repositorySlot = slot<User>()
        val eventSlot = slot<UserRegistrationCompletedEvent>()

        every { userRepository.findById(input.userId) } returns user
        every { customerRepository.findByUserId(user.id) } returns customer
        every { userRepository.save(capture(repositorySlot)) } answers { repositorySlot.captured }
        justRun { eventPublisher.publishEvent(capture(eventSlot)) }

        usecase.execute(input)

        val userSaved = repositorySlot.captured
        assertEquals(Status.ACTIVE, userSaved.status)

        val eventData = eventSlot.captured.getData()
        assertEquals(userSaved.id, eventData.userId)
        assertEquals(userSaved.username, eventData.username)
        assertEquals(userSaved.status, eventData.status)
        assertEquals(customer.name!!, eventData.name)
        assertEquals(customer.email, eventData.email)

        verify { userRepository.findById(input.userId) }
        verify { customerRepository.findByUserId(user.id) }
        verify { userRepository.save(repositorySlot.captured) }
        verify { eventPublisher.publishEvent(eventSlot.captured) }
    }
}
