package com.montebruni.holder.account.events.user

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.common.event.Event
import java.util.UUID

class UserCreatedEvent(override val entity: User, private val managerId: UUID? = null) : Event {

    override fun getData(): UserEventData = UserEventData(
        id = entity.id,
        username = entity.username,
        password = entity.password,
        status = entity.status,
        createdAt = entity.createdAt,
        managerId = managerId
    )
}
