package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.fixtures.createCustomer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.UUID

class CustomerCreatedEventTest {

    @Test
    fun `should get data from customer created event when manager id is not null`() {
        val entity = createCustomer().copy(createdAt = Instant.now())
        val managerId = UUID.randomUUID()

        val event = CustomerCreatedEvent(entity, managerId)
        val eventData = event.getData()

        assertEquals(entity.id, eventData.id)
        assertEquals(entity.userId, eventData.userId)
        assertEquals(entity.name, eventData.name)
        assertEquals(entity.email, eventData.email)
        assertNotNull(eventData.createdAt)
        assertEquals(managerId, eventData.managerId)
    }

    @Test
    fun `should get data from customer created event when manager id is null`() {
        val entity = createCustomer().copy(createdAt = Instant.now())

        val event = CustomerCreatedEvent(entity, null)
        val eventData = event.getData()

        assertEquals(entity.id, eventData.id)
        assertEquals(entity.userId, eventData.userId)
        assertEquals(entity.name, eventData.name)
        assertEquals(entity.email, eventData.email)
        assertNotNull(eventData.createdAt)
        assertNull(eventData.managerId)
    }
}
