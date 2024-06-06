package com.montebruni.holder.account.infrastructure.email

import com.montebruni.holder.account.application.email.MailSender
import com.montebruni.holder.account.application.email.data.MailData
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class MailSenderAdapter : MailSender {

    override fun send(data: MailData) {
        logger.debug { "Sending mail to ${data.to} with subject ${data.getSubject()} with body ${data.getBody()}" }
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}
