package com.montebruni.holder.account.domain.entity

import com.montebruni.holder.account.domain.exception.UserAlreadyRegisteredException
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username
import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val username: Username,
    val password: Password,
    val status: Status = Status.PENDING
) {

    fun isPending() = status == Status.PENDING

    fun canBeRegistered() = isPending().takeUnless { it.not() } ?: throw UserAlreadyRegisteredException()

    fun activate() = copy(status = Status.ACTIVE)
}
