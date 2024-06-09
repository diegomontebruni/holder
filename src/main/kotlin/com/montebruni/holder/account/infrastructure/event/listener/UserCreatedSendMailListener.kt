package com.montebruni.holder.account.infrastructure.event.listener

import com.montebruni.holder.account.application.email.MailSender
import com.montebruni.holder.account.application.email.data.UserCreatedMailData
import com.montebruni.holder.account.application.email.template.UserCreatedMailTemplate
import com.montebruni.holder.account.application.event.events.UserCreatedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import kotlin.let

@Component
class UserCreatedSendMailListener(
    private val mailSender: MailSender
) {

    @EventListener(classes = [UserCreatedEvent::class])
    fun sendPassword(event: UserCreatedEvent) {
        val eventData = event.getData()

        UserCreatedMailData(
            to = eventData.username!!.value,
            template = UserCreatedMailTemplate(eventData.password!!.value)
        ).let(mailSender::send)
    }
}
