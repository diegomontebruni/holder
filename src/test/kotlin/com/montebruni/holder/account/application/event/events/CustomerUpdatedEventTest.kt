package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.fixtures.createCustomer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.Instant

class CustomerUpdatedEventTest {

    @Test
    fun `should get data from customer update event successfully`() {
        val customer = createCustomer().copy(createdAt = Instant.now())
        val event = CustomerUpdatedEvent(customer)

        val eventData = event.getData()
        assertEquals(customer.id, eventData.id)
        assertEquals(customer.userId, eventData.userId)
        assertEquals(customer.name, eventData.name)
        assertEquals(customer.email, eventData.email)
        assertEquals(customer.createdAt, eventData.createdAt)
        assertNotNull(eventData.createdAt)
    }
}
