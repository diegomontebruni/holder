package com.montebruni.holder.account.domain.mail

import com.montebruni.holder.common.mail.MailTemplate
import kotlin.text.trimIndent

class UserCreatedMailTemplate(
    private val password: String
) : MailTemplate {

    override fun getBody(): String = """
        Your account has been created successfully. Your new password is $password.
    """.trimIndent()
}
