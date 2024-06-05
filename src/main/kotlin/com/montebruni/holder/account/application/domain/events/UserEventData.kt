package com.montebruni.holder.account.application.domain.events

import com.montebruni.holder.account.application.domain.entity.Status
import com.montebruni.holder.account.application.domain.valueobject.Password
import com.montebruni.holder.account.application.domain.valueobject.Username
import com.montebruni.holder.common.event.EventData
import java.util.UUID

data class UserEventData(
    val id: UUID? = null,
    val username: Username? = null,
    val password: Password? = null,
    val status: Status? = null,
    val managerId: UUID? = null
) : EventData
