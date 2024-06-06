package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.fixtures.createUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID

class UserCreatedEventTest {

    @Test
    fun `should get data from user created event when manager id is not null`() {
        val entity = createUser()
        val managerId = UUID.randomUUID()

        val event = UserCreatedEvent(entity, managerId)
        val eventData = event.getData()

        assertEquals(entity.id, eventData.id)
        assertEquals(entity.username, eventData.username)
        assertEquals(entity.password, eventData.password)
        assertEquals(entity.status, eventData.status)
        assertEquals(managerId, eventData.managerId)
    }

    @Test
    fun `should get data from user created event when manager id is null`() {
        val entity = createUser()

        val event = UserCreatedEvent(entity, null)
        val eventData = event.getData()

        assertEquals(entity.id, eventData.id)
        assertEquals(entity.username, eventData.username)
        assertEquals(entity.password, eventData.password)
        assertEquals(entity.status, eventData.status)
        assertNull(eventData.managerId)
    }
}
