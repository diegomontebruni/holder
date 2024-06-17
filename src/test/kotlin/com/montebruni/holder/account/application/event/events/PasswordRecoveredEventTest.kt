package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.fixtures.createUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PasswordRecoveredEventTest {

    @Test
    fun `should get data from PasswordRecoveredEvent successfully`() {
        val user = createUser()
        val eventData = PasswordRecoveredEvent(user).getData()

        assertEquals(user.username, eventData.username)
        assertEquals(user.password, eventData.password)
    }
}
