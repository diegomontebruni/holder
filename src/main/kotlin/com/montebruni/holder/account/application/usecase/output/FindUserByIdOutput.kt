package com.montebruni.holder.account.application.usecase.output

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.valueobject.Username
import java.util.UUID

data class FindUserByIdOutput(
    val id: UUID,
    val username: Username,
    val status: Status
)
