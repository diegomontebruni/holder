package com.montebruni.holder.account.usecase.input

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.common.valueobject.Password
import com.montebruni.holder.common.valueobject.Username
import java.util.UUID

data class CreateUserInput(
    val username: String,
    val password: String,
    val managerId: UUID? = null,
)

fun CreateUserInput.toUser() = User(
    username = Username(username),
    password = Password(password)
)
