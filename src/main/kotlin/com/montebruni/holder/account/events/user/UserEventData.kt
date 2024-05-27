package com.montebruni.holder.account.events.user

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.common.event.EventData
import java.time.Instant
import java.util.UUID

data class UserEventData(
    val id: UUID,
    val username: String,
    val password: String,
    val status: Status,
    val createdAt: Instant? = null,
    val managerId: UUID? = null
) : EventData
