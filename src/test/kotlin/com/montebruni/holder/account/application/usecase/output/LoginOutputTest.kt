package com.montebruni.holder.account.application.usecase.output

import com.montebruni.holder.account.domain.crypto.data.Token
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LoginOutputTest {

    @Test
    fun `should create a LoginOutput from a Token`() {
        val token = Token("token", 1234567890)
        val loginOutput = LoginOutput.from(token)

        assertEquals(token.value, loginOutput.accessToken)
        assertEquals(token.expiresAt, loginOutput.expiresAt)
    }
}
