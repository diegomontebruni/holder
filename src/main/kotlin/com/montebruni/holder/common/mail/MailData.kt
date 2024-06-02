package com.montebruni.holder.common.mail

interface MailData {
    val to: String
    val template: MailTemplate?
    fun getSubject(): String
    fun getBody(): String
}
