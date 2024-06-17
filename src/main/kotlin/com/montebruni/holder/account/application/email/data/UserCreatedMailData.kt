package com.montebruni.holder.account.application.email.data

import com.montebruni.holder.account.application.email.template.UserCreatedMailTemplate

data class UserCreatedMailData(
    override val to: String,
    override val template: UserCreatedMailTemplate
) : MailData {

    override fun getSubject(): String = "Welcome to our platform"

    override fun getBody(): String = template.getBody()
}
