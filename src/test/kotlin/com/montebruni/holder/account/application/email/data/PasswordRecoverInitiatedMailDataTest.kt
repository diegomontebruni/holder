package com.montebruni.holder.account.application.email.data

import com.montebruni.holder.account.application.email.template.MailTemplate
import com.montebruni.holder.configuration.UnitTests
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PasswordRecoverInitiatedMailDataTest : UnitTests() {

    private val email = "test@example.com"

    @Test
    fun `should return correct subject when getSubject is called`() {
        val mailData = PasswordRecoverInitiatedMailData(email, mockk())

        assertEquals("Password recovery requested", mailData.getSubject())
    }

    @Test
    fun `should return correct body when getBody is called`() {
        val template = mockk<MailTemplate>()

        every { template.getBody() } returns "Expected body"

        val mailData = PasswordRecoverInitiatedMailData(email, template)

        assertEquals("Expected body", mailData.getBody())
    }

    @Test
    fun `should return correct 'to' field when to is accessed`() {
        val mailData = PasswordRecoverInitiatedMailData(email, mockk())

        assertEquals(email, mailData.to)
    }
}
