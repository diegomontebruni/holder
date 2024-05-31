package com.montebruni.holder.account.events.customer

import com.montebruni.holder.account.domain.valueobject.Email
import com.montebruni.holder.common.event.EventData
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
