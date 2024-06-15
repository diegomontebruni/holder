package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.account.domain.valueobject.PasswordRecoverToken
import com.montebruni.holder.fixtures.RANDOM_PASSWORD_TOKEN
import com.montebruni.holder.fixtures.createUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant

class PasswordRecoveryInitiatedEventTest {

    @Test
    fun `should returns valid data when password recovery token and expiration are not null`() {
        val user = createUser().copy(
            passwordRecoverToken = RANDOM_PASSWORD_TOKEN.let(::PasswordRecoverToken),
            passwordRecoverTokenExpiration = Instant.now().plusSeconds(60)
        )
        val event = PasswordRecoveryInitiatedEvent(entity = user)

        val eventData = event.getData()

        assertEquals(user.username, eventData.username)
        assertEquals(user.passwordRecoverToken, eventData.passwordRecoverToken)
        assertNotNull(eventData.passwordRecoverTokenExpiration)
    }

    @Test
    fun `getData throws IllegalArgumentException when password recovery token is null`() {
        val user = createUser().copy(passwordRecoverTokenExpiration = Instant.now())

        val event = PasswordRecoveryInitiatedEvent(entity = user)

        assertThrows<IllegalArgumentException> {
            event.getData()
        }.run {
            assertEquals("Password recover token is null", message)
        }
    }

    @Test
    fun `getData throws IllegalArgumentException when password recovery token expiration is null`() {
        val user = createUser().copy(passwordRecoverToken = RANDOM_PASSWORD_TOKEN.let(::PasswordRecoverToken))
        val event = PasswordRecoveryInitiatedEvent(entity = user)

        assertThrows<IllegalArgumentException> {
            event.getData()
        }.run {
            assertEquals("Password recover token expiration is null", message)
        }
    }
}
