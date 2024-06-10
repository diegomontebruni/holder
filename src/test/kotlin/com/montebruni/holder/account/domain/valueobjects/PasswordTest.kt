package com.montebruni.holder.account.domain.valueobjects

import com.montebruni.holder.account.domain.crypto.PasswordEncryptor
import com.montebruni.holder.account.domain.valueobject.Password
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PasswordTest {

    @Autowired
    private lateinit var passwordEncryptor: PasswordEncryptor

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

        @Test
        fun `should not be encrypt password if it is already encrypted`() {
            val password = Password().encrypt(passwordEncryptor)
            val encryptedPassword = Password(password.value)

            assertEquals(password.value, encryptedPassword.value)
        }

        @Test
        fun `should encrypt the password and be different from the original password`() {
            val password = Password()
            val encryptedPassword = password.encrypt(passwordEncryptor)
            assertNotEquals(password.value, encryptedPassword.value)
        }

        @Test
        fun `Password should be validated correctly`() {
            val password = Password()
            val encryptedPassword = password.encrypt(passwordEncryptor)
            assertTrue(password.validate(encryptedPassword.value, passwordEncryptor))
        }
    }
}
