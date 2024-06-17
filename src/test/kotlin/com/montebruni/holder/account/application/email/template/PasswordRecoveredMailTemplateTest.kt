package com.montebruni.holder.account.application.email.template

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PasswordRecoveredMailTemplateTest {

    @Test
    fun `should getBody when given parameters then return correct body`() {
        val recoveredPassword = "password"

        val template = PasswordRecoveredMailTemplate(recoveredPassword)

        val body = template.getBody()

        val expectedBody = """
            Hello, your password has been successfully recovered.
            Your new password is: $recoveredPassword
        """.trimIndent()

        assertEquals(expectedBody, body)
    }
}
