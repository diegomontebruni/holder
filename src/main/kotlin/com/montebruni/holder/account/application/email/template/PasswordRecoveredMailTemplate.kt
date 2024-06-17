package com.montebruni.holder.account.application.email.template

data class PasswordRecoveredMailTemplate(
    private val recoveredPassword: String
) : MailTemplate {

    override fun getBody(): String =
        """
            Hello, your password has been successfully recovered.
            Your new password is: $recoveredPassword
        """.trimIndent()
}
