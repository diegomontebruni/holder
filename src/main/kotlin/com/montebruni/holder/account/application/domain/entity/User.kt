package com.montebruni.holder.account.application.domain.entity

import com.montebruni.holder.account.application.domain.valueobject.Password
import com.montebruni.holder.account.application.domain.valueobject.Username
import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val username: Username,
    val password: Password,
    val status: Status = Status.PENDING
)
