package com.montebruni.holder.account.presentation.rest.response

import com.montebruni.holder.fixtures.createLoginOutput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LoginResponseTest {

    @Test
    fun `should create from output`() {
        val output = createLoginOutput()
        val response = LoginResponse.from(output)

        assertEquals(output.accessToken, response.accessToken)
        assertEquals(output.expiresAt, response.expiresAt)
    }
}
