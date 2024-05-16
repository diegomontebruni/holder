package com.montebruni.holder.account.domain.entity

import java.time.Instant
import java.util.UUID

data class User(
    val id: UUID,
    val name: String,
    val createdAt: Instant
)
