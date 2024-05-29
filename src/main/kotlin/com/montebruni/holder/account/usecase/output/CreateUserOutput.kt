package com.montebruni.holder.account.usecase.output

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.entity.User
import java.util.UUID

data class CreateUserOutput(
    val id: UUID,
    val status: Status
) {
    companion object
}

fun CreateUserOutput.Companion.fromUser(user: User) = CreateUserOutput(
    id = user.id,
    status = user.status
)
