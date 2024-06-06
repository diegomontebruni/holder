package com.montebruni.holder.account.application.email.data

import com.montebruni.holder.account.application.email.template.MailTemplate

interface MailData {

    val to: String
    val template: MailTemplate?
    fun getSubject(): String
    fun getBody(): String
}
