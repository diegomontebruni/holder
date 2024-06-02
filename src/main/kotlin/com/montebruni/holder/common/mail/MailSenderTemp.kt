package com.montebruni.holder.common.mail

import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class MailSenderTemp : MailSender {

    override fun send() {
        logger.debug { "Sending mail" }
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}
