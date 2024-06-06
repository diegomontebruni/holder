package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.fixtures.createUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CustomerRegistrationCompletedEventTest {

    @Test
    fun `should get data from event successfully`() {
        val user = createUser()
        val name = "John Doe"

        val event = CustomerRegistrationCompletedEvent(user, name)
        val eventData = event.getData()

        assertEquals(user.id, eventData.userId)
        assertEquals(name, eventData.name)
    }
}
