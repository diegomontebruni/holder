package com.montebruni.holder.account.application.domain.entity

import com.montebruni.holder.account.application.domain.valueobject.Email
import java.time.Instant
import java.util.UUID

data class Customer(
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val name: String? = null,
    val email: Email,
    val createdAt: Instant? = null
)
