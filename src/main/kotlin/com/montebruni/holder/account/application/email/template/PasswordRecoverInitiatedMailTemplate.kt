package com.montebruni.holder.account.application.email.template

import com.montebruni.holder.account.application.extension.getFormattedDate
import com.montebruni.holder.account.application.extension.getFormattedTime
import org.springframework.beans.factory.annotation.Value
import java.time.Instant

data class PasswordRecoverInitiatedMailTemplate(
    private val token: String,
    private val expirationDate: Instant
) : MailTemplate {

    @Value("\${app.url.recover-password}")
    private lateinit var recoverPasswordUrl: String

    override fun getBody(): String {
        return """
            Hello, we received a request to recover your password. If you did not request this, please ignore this email.
            The link to reset your password is: $recoverPasswordUrl?token=$token
            It will expire on: ${expirationDate.getFormattedDate()} at ${expirationDate.getFormattedTime()}
        """.trimIndent()
    }
}
