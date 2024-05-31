package com.montebruni.holder.account.events.user

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.common.event.EventData
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username
import java.util.UUID

data class UserEventData(
    val id: UUID? = null,
    val username: Username? = null,
    val password: Password? = null,
    val status: Status? = null,
    val managerId: UUID? = null
) : EventData
