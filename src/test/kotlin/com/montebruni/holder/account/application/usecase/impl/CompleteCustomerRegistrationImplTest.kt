package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.CustomerRegistrationCompletedEvent
import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCompleteCustomerRegistrationInput
import com.montebruni.holder.fixtures.createUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CompleteCustomerRegistrationImplTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: CompleteCustomerRegistrationImpl

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
    }
}
