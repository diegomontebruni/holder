package com.montebruni.holder.account.domain.valueobjects

import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.configuration.UnitTests
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PasswordTest : UnitTests() {

    @MockK lateinit var encryptorProvider: EncryptorProvider

    @Nested
    inner class ConstructorCases {

        @Test
        fun `should create a password with a random value`() {
            val password = Password()
            assertEquals(12, password.value.length)
        }

        @Test
        fun `should create a password with a specific value`() {
            val password = Password("A12$3c456789")
            assertEquals("A12$3c456789", password.value)
        }

        @Test
        fun `should create a password when contains a mix of valid characters`() {
            val password = Password()
            assertTrue(password.value.any { it.isDigit() })
            assertTrue(password.value.any { it.isLowerCase() })
            assertTrue(password.value.any { it.isUpperCase() })
            assertTrue(password.value.any { it in "!@#$%^&*()-_=+{}[]|:;<>?,./" })
        }

        @ParameterizedTest
        @CsvSource(
            "A12$, Password length should be between 6 and 12",
            "A12$3c4567890, Password length should be between 6 and 12",
            "A#cdEfghij, Password should contain a digit",
            "A#CDEFGHIJ1, Password should contain a lowercase letter",
            "a#bcdefghij1, Password should contain an uppercase letter",
            "A12cdEfghij1, Password should contain a special character"
        )
        fun `should throw exception when create a password with invalid values`(value: String, errorMessage: String) {
            assertThrows<IllegalArgumentException> { Password(value) }.run {
                assertEquals(errorMessage, message)
            }
        }
    }

    @Nested
    inner class EncryptCases {

        private val passwordEncrypted =
            "ecg5ljx4WPaEo0xZiIKImA==:$2a$10\$BKEYJQILWBsV2cEdikOuCuQ1iaIuuJSaDYa7BtNDnnJgg9VkS5Nm2"

        @Test
        fun `should not be encrypt password if it is already encrypted`() {
            val password = Password(passwordEncrypted)
            val encryptedPassword = Password(password.value)

            assertEquals(password.value, encryptedPassword.value)

            verify(exactly = 0) { encryptorProvider.encrypt(any()) }
        }

        @Test
        fun `should encrypt the password and be different from the original password`() {
            val password = Password()

            every { encryptorProvider.encrypt(any()) } returns passwordEncrypted

            val encryptedPassword = password.encrypt(encryptorProvider)

            assertNotEquals(password.value, encryptedPassword.value)

            verify { encryptorProvider.encrypt(password.value) }
        }

        @Test
        fun `Password should be validated correctly`() {
            val password = Password()
            val encryptSlot = slot<String>()
            val valueValidateSlot = slot<String>()
            val encryptedValueValidateSlot = slot<String>()

            every { encryptorProvider.encrypt(capture(encryptSlot)) } returns passwordEncrypted
            every {
                encryptorProvider.validate(capture(valueValidateSlot), capture(encryptedValueValidateSlot))
            } returns true

            val encryptedPassword = password.encrypt(encryptorProvider)

            assertTrue(password.validate(encryptedPassword.value, encryptorProvider))

            assertEquals(password.value, encryptSlot.captured)
            assertEquals(password.value, valueValidateSlot.captured)
            assertEquals(encryptedPassword.value, encryptedValueValidateSlot.captured)

            verify { encryptorProvider.encrypt(encryptSlot.captured) }
            verify { encryptorProvider.validate(valueValidateSlot.captured, encryptedValueValidateSlot.captured) }
        }
    }
}
