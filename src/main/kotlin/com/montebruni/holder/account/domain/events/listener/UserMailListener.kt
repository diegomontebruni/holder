package com.montebruni.holder.account.domain.events.listener

import com.montebruni.holder.account.domain.events.data.UserCreatedEvent
import com.montebruni.holder.account.domain.mail.UserCreatedMailData
import com.montebruni.holder.account.domain.mail.UserCreatedMailTemplate
import com.montebruni.holder.common.mail.MailSender
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import kotlin.let

@Component
class UserMailListener(
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
