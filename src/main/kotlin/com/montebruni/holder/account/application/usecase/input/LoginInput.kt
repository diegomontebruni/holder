package com.montebruni.holder.account.application.usecase.input

import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username

data class LoginInput(
    val username: Username,
    val password: Password
)
