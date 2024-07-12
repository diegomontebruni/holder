package com.montebruni.holder.fixtures

import com.montebruni.holder.account.application.usecase.input.LoginInput
import com.montebruni.holder.account.application.usecase.output.LoginOutput
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username
import com.montebruni.holder.account.presentation.rest.request.LoginRequest

fun createLoginInput() = LoginInput(
    username = Username("john.doe@jd.com"),
    password = Password("abCD@0912")
)

fun createLoginOutput() = LoginOutput(
    accessToken = "TOKEN",
    expiresAt = 123456789
)

fun createLoginRequest() = LoginRequest(
    username = "john.doe@jd.com",
    password = "abCD@0912"
)
