package com.montebruni.holder.account.domain.valueobjects

import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import com.montebruni.holder.account.domain.valueobject.PasswordRecoverToken
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.RANDOM_PASSWORD_TOKEN
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PasswordRecoverTokenTest : UnitTests() {

    @MockK lateinit var encryptorProvider: EncryptorProvider

    @Test
    fun `should generate random token correctly`() {
        val randomToken = RANDOM_PASSWORD_TOKEN

        every { encryptorProvider.randomToken() } returns randomToken

        val token = PasswordRecoverToken.generateRandomToken(encryptorProvider)

        assertTrue(token.validate(randomToken))

        verify { encryptorProvider.randomToken() }
    }

    @Test
    fun `should throw error when token is invalid`() {
        val invalidToken = "invalidToken"

        assertThrows<IllegalArgumentException> {
            PasswordRecoverToken(invalidToken)
        }.run {
            assertEquals("Invalid password recover token", this.message)
        }

        verify(exactly = 0) { encryptorProvider.randomToken() }
    }
}
