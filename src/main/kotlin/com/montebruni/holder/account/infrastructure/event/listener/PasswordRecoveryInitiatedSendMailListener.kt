package com.montebruni.holder.account.infrastructure.event.listener

import com.montebruni.holder.account.application.email.MailSender
import com.montebruni.holder.account.application.email.data.PasswordRecoverInitiatedMailData
import com.montebruni.holder.account.application.email.template.PasswordRecoverInitiatedMailTemplate
import com.montebruni.holder.account.application.event.events.PasswordRecoveryInitiatedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PasswordRecoveryInitiatedSendMailListener(
    private val mailSender: MailSender
) {

    @EventListener(classes = [PasswordRecoveryInitiatedEvent::class])
    fun send(event: PasswordRecoveryInitiatedEvent) {
        val eventData = event.getData()

        PasswordRecoverInitiatedMailData(
            to = eventData.username.value,
            template = PasswordRecoverInitiatedMailTemplate(
                token = eventData.passwordRecoverToken.value,
                expirationDate = eventData.passwordRecoverTokenExpiration
            )
        ).let(mailSender::send)
    }
}
