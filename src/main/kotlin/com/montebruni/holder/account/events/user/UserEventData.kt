package com.montebruni.holder.account.events.user

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.common.event.EventData
import com.montebruni.holder.common.valueobject.Password
import com.montebruni.holder.common.valueobject.Username
import java.util.UUID

data class UserEventData(
    val id: UUID,
    val username: Username,
    val password: Password,
    val status: Status,
    val managerId: UUID? = null
) : EventData
