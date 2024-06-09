package com.montebruni.holder.account.domain.valueobjects

import com.montebruni.holder.account.domain.valueobject.Email
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class EmailTest {

    @ParameterizedTest
    @CsvSource(
        "test@example.com, true",
        "test@dev.io, true",
        "test.example.com, false",
        "test@.com, false",
        "test@com, false",
        "test@, false"
    )
    fun `should validate email according to provided emails`(email: String, isValid: Boolean) {
        if (isValid) {
            assertDoesNotThrow { Email(email) }
        } else {
            assertThrows<IllegalArgumentException> { Email(email) }.run {
                assertEquals(message, "Invalid email")
            }
        }
    }
}
