package com.montebruni.holder.account.domain.mail

import com.montebruni.holder.common.mail.MailData
import com.montebruni.holder.common.mail.MailTemplate

data class UserCreatedMailData(
    override val to: String,
    override val template: MailTemplate
) : MailData {

    override fun getSubject(): String = "Welcome to our platform"

    override fun getBody(): String = template.getBody()
}
