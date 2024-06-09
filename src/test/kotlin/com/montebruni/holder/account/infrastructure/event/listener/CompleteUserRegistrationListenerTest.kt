package com.montebruni.holder.account.infrastructure.event.listener

import com.montebruni.holder.account.application.usecase.CompleteUserRegistration
import com.montebruni.holder.account.application.usecase.input.CompleteUserRegistrationInput
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCustomerRegistrationCompletedEvent
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CompleteUserRegistrationListenerTest(
    @MockK private val completeUserRegistration: CompleteUserRegistration
) : UnitTests() {

    @InjectMockKs
    private lateinit var listener: CompleteUserRegistrationListener

    @Test
    fun `should call use case successfully`() {
        val event = createCustomerRegistrationCompletedEvent()
        val inputSlot = slot<CompleteUserRegistrationInput>()

        justRun { completeUserRegistration.execute(capture(inputSlot)) }

        listener.listener(event)

        val inputCaptured = inputSlot.captured
        assertEquals(event.getData().userId, inputCaptured.userId)
    }
}
