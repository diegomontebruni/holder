package com.montebruni.holder.account.presentation.event

import com.montebruni.holder.account.application.usecase.CreateCustomer
import com.montebruni.holder.account.application.usecase.input.CreateCustomerInput
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createUserCreatedEvent
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateCustomerListenerTest(
    @MockK private val createCustomer: CreateCustomer
) : UnitTests() {

    @InjectMockKs
    private lateinit var listener: CreateCustomerListener

    @Test
    fun `should call use case successfully`() {
        val event = createUserCreatedEvent()
        val inputSlot = slot<CreateCustomerInput>()

        every { createCustomer.execute(capture(inputSlot)) } returns mockk()

        listener.listener(event)

        val inputCaptured = inputSlot.captured
        assertEquals(event.getData().id, inputCaptured.userId)
        assertEquals(event.getData().username?.value, inputCaptured.email.value)
        assertEquals(event.getData().managerId, inputCaptured.managerId)

        verify { createCustomer.execute(inputSlot.captured) }
    }
}
