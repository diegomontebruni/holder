package com.montebruni.holder.account.domain.events.data

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username
import com.montebruni.holder.common.event.EventData
import java.util.UUID

data class UserEventData(
    val id: UUID? = null,
    val username: Username? = null,
    val password: Password? = null,
    val status: Status? = null,
    val managerId: UUID? = null
) : EventData
