package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.account.application.usecase.input.LoginInput
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Login request")
data class LoginRequest(
    @Schema(description = "Username", example = "john.doe")
    val username: String,
    @Schema(description = "Password", example = "123456")
    val password: String
)

fun LoginRequest.toInput() = LoginInput(
    username = Username(username),
    password = Password(password)
)
