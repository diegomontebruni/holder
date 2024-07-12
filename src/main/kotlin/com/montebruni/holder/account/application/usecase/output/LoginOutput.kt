package com.montebruni.holder.account.application.usecase.output

import com.montebruni.holder.account.domain.crypto.data.Token

data class LoginOutput(
    val accessToken: String,
    val expiresAt: Long
) {
    companion object
}

fun LoginOutput.Companion.from(token: Token) = LoginOutput(
    accessToken = token.value,
    expiresAt = token.expiresAt
)
