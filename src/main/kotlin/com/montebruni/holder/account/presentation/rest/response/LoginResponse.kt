package com.montebruni.holder.account.presentation.rest.response

import com.montebruni.holder.account.application.usecase.output.LoginOutput

data class LoginResponse(
    val accessToken: String,
    val expiresAt: Long
) {
    companion object
}

fun LoginResponse.Companion.from(output: LoginOutput) = LoginResponse(
    accessToken = output.accessToken,
    expiresAt = output.expiresAt
)
