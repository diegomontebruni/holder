package com.montebruni.holder.account.infrastructure.crypto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BCryptEncryptorTest {

    private val bCryptEncryptor: BCryptEncryptor = BCryptEncryptor()

    @ParameterizedTest
    @ValueSource(
        strings = [
            "passwa",
            "1234567",
            "password",
            "123456789",
            "password12",
            "password123",
            "password1234",
        ]
    )
    fun `should encrypt password correctly`(password: String) {
        val encryptedPassword = bCryptEncryptor.encrypt(password)

        val salt = encryptedPassword.split(":").first()
        val passwordEncrypted = encryptedPassword.split(":").last()

        assertTrue(encryptedPassword.contains(":"))
        assertEquals(salt.length, 24)
        assertEquals(passwordEncrypted.length, 60)
        assertNotEquals(password, encryptedPassword)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "passwa",
            "1234567",
            "password",
            "123456789",
            "password12",
            "password123",
            "password1234",
        ]
    )
    fun `should validate encrypted password correctly`(password: String) {
        val encryptedPassword = bCryptEncryptor.encrypt(password)

        assertTrue(bCryptEncryptor.validate(password, encryptedPassword))
        assertFalse(bCryptEncryptor.validate("wrongpassword", encryptedPassword))
    }
}
