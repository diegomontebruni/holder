package com.montebruni.holder.account.domain.valueobjects

import com.montebruni.holder.account.domain.valueobject.Password
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PasswordTest {

    @Test
    fun `Password length should be 12`() {
        val password = Password()
        assertEquals(12, password.value.length)
    }

    @Test
    fun `Password should contain a mix of characters`() {
        val password = Password()
        assertTrue(password.value.any { it.isDigit() })
        assertTrue(password.value.any { it.isLowerCase() })
        assertTrue(password.value.any { it.isUpperCase() })
        assertTrue(password.value.any { it in "!@#$%^&*()-_=+{}[]|:;<>?,./" })
    }
}
