package com.montebruni.holder.account.application.email.data

import com.montebruni.holder.account.application.email.template.PasswordRecoveredMailTemplate
import com.montebruni.holder.configuration.UnitTests
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PasswordRecoveredMailDataTest : UnitTests() {

    @Test
    fun `should create mail data with correct subject and body`() {
        val email = "john.snow@winter.com"
        val recoveredPassword = "recoveredPassword"

        val mailTemplate = PasswordRecoveredMailTemplate(recoveredPassword)
        val mailData = PasswordRecoveredMailData(email, mailTemplate)

        assertEquals("Password recovered", mailData.getSubject())
        assertEquals(mailTemplate.getBody(), mailData.getBody())
        assertEquals(email, mailData.to)
    }
}
