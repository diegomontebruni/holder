package com.montebruni.holder.account.infrastructure.event.listener

import com.montebruni.holder.account.application.email.MailSender
import com.montebruni.holder.account.application.email.data.PasswordRecoveredMailData
import com.montebruni.holder.account.application.email.template.PasswordRecoveredMailTemplate
import com.montebruni.holder.account.application.event.events.PasswordRecoveredEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PasswordRecoveredSendMailListener(
    private val mailSender: MailSender
) {

    @EventListener(classes = [PasswordRecoveredEvent::class])
    fun send(event: PasswordRecoveredEvent) {
        val eventData = event.getData()

        PasswordRecoveredMailData(
            to = eventData.username!!.value,
            template = PasswordRecoveredMailTemplate(
                recoveredPassword = eventData.password!!.value,
            )
        ).let(mailSender::send)
    }
}
