package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.fixtures.createCreateUserRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateUserRequestTest {

    @Test
    fun `should convert CreateUserRequest to CreateUserInput`() {
        val request = createCreateUserRequest()
        val input = request.toCreateUserInput()

        assertEquals(request.username, input.username.value)
    }

    @Test
    fun `should throw exception when username is not a valid email`() {
        val request = createCreateUserRequest().copy(username = "invalid-email")

        assertThrows<IllegalArgumentException> { request.toCreateUserInput() }.run {
            assertEquals("The username must be a valid email", message)
        }
    }
}
