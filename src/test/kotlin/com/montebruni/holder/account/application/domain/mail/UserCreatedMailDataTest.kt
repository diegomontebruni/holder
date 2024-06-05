package com.montebruni.holder.account.application.domain.mail

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserCreatedMailDataTest {

    @Test
    fun `should create mail data with correct subject and body`() {
        val email = "john.snow@winter.com"
        val password = "password"

        val mailTemplate = UserCreatedMailTemplate(password)
        val mailData = UserCreatedMailData(email, mailTemplate)

        assertEquals("Welcome to our platform", mailData.getSubject())
        assertEquals(mailTemplate.getBody(), mailData.getBody())
        assertEquals(email, mailData.to)
    }
}
