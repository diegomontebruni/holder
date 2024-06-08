package com.montebruni.holder.account.infrastructure.event.listener

import com.montebruni.holder.account.application.usecase.UpdateCustomer
import com.montebruni.holder.account.application.usecase.input.UpdateCustomerInput
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCustomerRegistrationCompletedEvent
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UpdateCustomerListenerTest(
    @MockK private val updateCustomer: UpdateCustomer
) : UnitTests() {

    @InjectMockKs
    private lateinit var listener: UpdateCustomerListener

    @Test
    fun `should call use case successfully`() {
        val event = createCustomerRegistrationCompletedEvent()
        val inputSlot = slot<UpdateCustomerInput>()

        justRun { updateCustomer.execute(capture(inputSlot)) }

        listener.listener(event)

        val eventData = event.getData()
        assertEquals(eventData.id, inputSlot.captured.id)
        assertEquals(eventData.name, inputSlot.captured.name)

        verify { updateCustomer.execute(inputSlot.captured) }
    }
}
