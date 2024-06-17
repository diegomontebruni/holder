package com.montebruni.holder.account.application.email.data

import com.montebruni.holder.account.application.email.template.PasswordRecoveredMailTemplate

class PasswordRecoveredMailData(
    override val to: String,
    override val template: PasswordRecoveredMailTemplate
) : MailData {

    override fun getSubject(): String = "Password recovered"

    override fun getBody(): String = template.getBody()
}
