package com.montebruni.holder.account.infrastructure.event.listener

import com.montebruni.holder.account.application.email.MailSender
import com.montebruni.holder.account.application.email.data.PasswordRecoveredMailData
import com.montebruni.holder.account.application.event.events.PasswordRecoveredEvent
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createUser
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PasswordRecoveredSendMailListenerTest(
    @MockK private val mailSender: MailSender
) : UnitTests() {

    @InjectMockKs
    private lateinit var listener: PasswordRecoveredSendMailListener

    @Test
    fun `should send email from event`() {
        val event = PasswordRecoveredEvent(createUser())
        val mailDataSlot = slot<PasswordRecoveredMailData>()

        justRun { mailSender.send(capture(mailDataSlot)) }

        listener.send(event)

        val eventData = event.getData()
        assertEquals(eventData.username!!.value, mailDataSlot.captured.to)

        verify(exactly = 1) { mailSender.send(mailDataSlot.captured) }
    }
}
