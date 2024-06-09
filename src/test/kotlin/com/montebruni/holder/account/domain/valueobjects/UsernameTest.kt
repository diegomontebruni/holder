package com.montebruni.holder.account.domain.valueobjects

import com.montebruni.holder.account.domain.valueobject.Username
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class UsernameTest {

    @ParameterizedTest
    @CsvSource(
        "test@example.com, true",
        "test@dev.io, true",
        "john snow, false",
        "jose fei, false",
        "test, false",
        "jsm, false"
    )
    fun `should validate username according to provided values`(value: String, isValid: Boolean) {
        if (isValid) {
            assertDoesNotThrow { Username(value) }
        } else {
            assertThrows<IllegalArgumentException> { Username(value) }.run {
                assertEquals(message, "The username must be a valid email")
            }
        }
    }
}
