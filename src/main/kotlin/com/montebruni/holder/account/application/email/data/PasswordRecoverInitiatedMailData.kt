package com.montebruni.holder.account.application.email.data

import com.montebruni.holder.account.application.email.template.MailTemplate

class PasswordRecoverInitiatedMailData(
    override val to: String,
    override val template: MailTemplate
) : MailData {

    override fun getSubject(): String = "Password recovery requested"

    override fun getBody(): String = template.getBody()
}
