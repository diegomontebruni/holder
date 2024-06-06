package com.montebruni.holder.account.domain.event

import com.montebruni.holder.account.domain.valueobject.Email
import java.time.Instant
import java.util.UUID

data class CustomerEventData(
    val id: UUID? = null,
    val userId: UUID? = null,
    val name: String? = null,
    val email: Email? = null,
    val createdAt: Instant? = null,
    val managerId: UUID? = null
) : EventData
