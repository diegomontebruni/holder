package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.fixtures.createLoginRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LoginRequestTest {

    @Test
    fun `should convert to input`() {
        val loginRequest = createLoginRequest()
        val input = loginRequest.toInput()

        assertEquals(loginRequest.username, input.username.value)
        assertEquals(loginRequest.password, input.password.value)
    }

    @Test
    fun `should throw error when try to convert to input with invalid username`() {
        val loginRequest = createLoginRequest().copy(username = "username")

        assertThrows<IllegalArgumentException> { loginRequest.toInput() }
    }

    @Test
    fun `should throw error when try to convert to input with invalid password`() {
        val loginRequest = createLoginRequest().copy(password = "password")

        assertThrows<IllegalArgumentException> { loginRequest.toInput() }
    }
}
