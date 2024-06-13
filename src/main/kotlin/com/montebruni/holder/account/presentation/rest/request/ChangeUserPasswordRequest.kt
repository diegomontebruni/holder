package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.account.application.usecase.input.ChangeUserPasswordInput
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username

data class ChangeUserPasswordRequest(
    val username: String,
    val oldPassword: String,
    val newPassword: String,
)

fun ChangeUserPasswordRequest.toInput() = ChangeUserPasswordInput(
    username = Username(username),
    oldPassword = Password(oldPassword),
    newPassword = Password(newPassword),
)
