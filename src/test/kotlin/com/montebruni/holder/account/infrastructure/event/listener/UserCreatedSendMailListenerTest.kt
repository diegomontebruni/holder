package com.montebruni.holder.account.infrastructure.event.listener

import com.montebruni.holder.account.application.email.MailSender
import com.montebruni.holder.account.application.email.data.UserCreatedMailData
import com.montebruni.holder.account.application.email.template.UserCreatedMailTemplate
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createUserCreatedEvent
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserCreatedSendMailListenerTest(
    @MockK private val mailSender: MailSender
) : UnitTests() {

    @InjectMockKs
    private lateinit var listener: UserCreatedSendMailListener

    @Test
    fun `should send mail successfully`() {
        val event = createUserCreatedEvent()
        val mailDataSlot = slot<UserCreatedMailData>()

        val template = UserCreatedMailData(
            to = event.getData().username!!.value,
            template = UserCreatedMailTemplate(event.getData().password!!.value)
        )

        justRun { mailSender.send(capture(mailDataSlot)) }

        listener.sendPassword(event)

        assertEquals(event.getData().username!!.value, mailDataSlot.captured.to)
        assertEquals(template.getSubject(), mailDataSlot.captured.getSubject())
        assertEquals(template.getBody(), mailDataSlot.captured.getBody())

        verify { mailSender.send(mailDataSlot.captured) }
    }
}
