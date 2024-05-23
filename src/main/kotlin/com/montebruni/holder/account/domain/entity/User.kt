package com.montebruni.holder.account.domain.entity

import java.time.Instant
import java.util.UUID

data class User(
    val id: UUID,
    val username: String,
    val password: String,
    val status: Status = Status.ACTIVE,
    val createdAt: Instant
)
