package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.fixtures.createCustomer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CustomerRegistrationCompletedEventTest {

    @Test
    fun `should get data from event successfully`() {
        val customer = createCustomer()

        val event = CustomerRegistrationCompletedEvent(customer)
        val eventData = event.getData()

        assertEquals(customer.userId, eventData.userId)
        assertEquals(customer.name, eventData.name)
    }
}
