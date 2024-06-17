package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.account.application.usecase.input.ChangeUserPasswordInput
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Change User Password Request")
data class ChangeUserPasswordRequest(
    @Schema(description = "Username", example = "john@john.com")
    val username: String,
    @Schema(description = "Old Password", example = "oldPassword")
    val oldPassword: String,
    @Schema(description = "New Password", example = "newPassword")
    val newPassword: String,
)

fun ChangeUserPasswordRequest.toInput() = ChangeUserPasswordInput(
    username = Username(username),
    oldPassword = Password(oldPassword),
    newPassword = Password(newPassword),
)
