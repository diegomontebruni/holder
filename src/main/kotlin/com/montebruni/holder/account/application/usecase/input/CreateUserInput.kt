package com.montebruni.holder.account.application.usecase.input

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username
import java.util.UUID

data class CreateUserInput(
    val username: Username,
    val managerId: UUID? = null,
)

fun CreateUserInput.toUser() = User(
    username = username,
    password = Password()
)
