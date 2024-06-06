package com.montebruni.holder.account.application.email.data

import com.montebruni.holder.account.application.email.template.MailTemplate

data class UserCreatedMailData(
    override val to: String,
    override val template: MailTemplate
) : MailData {

    override fun getSubject(): String = "Welcome to our platform"

    override fun getBody(): String = template.getBody()
}
