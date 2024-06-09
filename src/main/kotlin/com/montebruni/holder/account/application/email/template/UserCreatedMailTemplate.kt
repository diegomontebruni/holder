package com.montebruni.holder.account.application.email.template

import kotlin.text.trimIndent

class UserCreatedMailTemplate(
    private val password: String
) : MailTemplate {

    override fun getBody(): String = """
        Your account has been created successfully. Your password is $password.
    """.trimIndent()
}
