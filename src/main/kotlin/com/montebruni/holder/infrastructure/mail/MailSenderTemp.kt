package com.montebruni.holder.infrastructure.mail

import com.montebruni.holder.common.mail.MailData
import com.montebruni.holder.common.mail.MailSender
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class MailSenderTemp : MailSender {

    override fun send(data: MailData) {
        logger.debug { "Sending mail to ${data.to} with subject ${data.getSubject()} with body ${data.getBody()}" }
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}
