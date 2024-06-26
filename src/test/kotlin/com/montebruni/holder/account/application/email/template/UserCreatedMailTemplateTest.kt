package com.montebruni.holder.account.application.email.template

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserCreatedMailTemplateTest {

    @Test
    fun `should create mail template with password`() {
        val password = "password"
        val template = UserCreatedMailTemplate(password)

        val body = template.getBody()

        assertEquals("Your account has been created successfully. Your password is: $password", body)
    }
}
