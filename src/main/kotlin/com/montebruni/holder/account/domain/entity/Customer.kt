package com.montebruni.holder.account.domain.entity

import java.time.Instant
import java.util.UUID

data class Customer(
    val id: UUID,
    val userId: UUID,
    val name: String,
    val email: String,
    val createdAt: Instant? = null
)
