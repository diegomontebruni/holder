package com.montebruni.holder.account.application.email.template

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.test.util.ReflectionTestUtils
import java.time.Instant

class PasswordRecoverInitiatedMailTemplateTest {

    @Test
    fun `should getBody when given parameters then return correct body`() {
        val recoverPasswordUrl = "http://localhost:8080/recover-password"
        val token = "token"
        val expirationDate = Instant.parse("2024-06-15T15:25:00Z")

        val expectedDate = "15/06/2024"
        val expectedTime = "12:25"

        val template = PasswordRecoverInitiatedMailTemplate(token, expirationDate)
        ReflectionTestUtils.setField(template, "recoverPasswordUrl", recoverPasswordUrl)

        val body = template.getBody()

        val expectedBody = """
            Hello, we received a request to recover your password. If you did not request this, please ignore this email.
            The link to reset your password is: $recoverPasswordUrl?token=$token
            It will expire on: $expectedDate at $expectedTime
        """.trimIndent()

        assertEquals(expectedBody, body)
    }
}
