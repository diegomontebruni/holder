package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.CustomerRegistrationCompletedEvent
import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.exception.UserAlreadyRegisteredException
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCompleteCustomerRegistrationInput
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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class CompleteCustomerRegistrationImplTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: CompleteCustomerRegistrationImpl

    @Test
    fun `should throw user not found exception when user not found`() {
        val input = createCompleteCustomerRegistrationInput()

        every { userRepository.findById(input.userId) } returns null

        assertThrows<UserNotFoundException> { usecase.execute(input) }

        verify(exactly = 1) { userRepository.findById(input.userId) }
        verify(exactly = 0) { eventPublisher.publishEvent(any()) }
    }

    @ParameterizedTest
    @EnumSource(Status::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
    fun `should throw user already registered exception when user is not pending`(status: Status) {
        val input = createCompleteCustomerRegistrationInput()
        val user = createUser().copy(status = status)

        every { userRepository.findById(input.userId) } returns user

        assertThrows<UserAlreadyRegisteredException> { usecase.execute(input) }

        verify(exactly = 1) { userRepository.findById(input.userId) }
        verify(exactly = 0) { eventPublisher.publishEvent(any()) }
    }

    @Test
    fun `should complete customer registration successfully`() {
        val input = createCompleteCustomerRegistrationInput()
        val user = createUser().copy(id = input.userId)
        val eventSlot = slot<CustomerRegistrationCompletedEvent>()

        every { userRepository.findById(input.userId) } returns user
        justRun { eventPublisher.publishEvent(capture(eventSlot)) }

        usecase.execute(input)

        val event = eventSlot.captured.getData()
        assertEquals(input.userId, event.userId)
        assertEquals(input.name, event.name)

        verify {
            userRepository.findById(input.userId)
            eventPublisher.publishEvent(eventSlot.captured)
        }
    }
}
