package com.montebruni.holder.account.application.email

import com.montebruni.holder.account.application.email.data.MailData

interface MailSender {

    fun send(data: MailData)
}
