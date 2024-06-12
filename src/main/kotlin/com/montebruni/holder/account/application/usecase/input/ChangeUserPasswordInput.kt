package com.montebruni.holder.account.application.usecase.input

import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username

data class ChangeUserPasswordInput(
    val username: Username,
    val oldPassword: Password,
    val newPassword: Password
)
