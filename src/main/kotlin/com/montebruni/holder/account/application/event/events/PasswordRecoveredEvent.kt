package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.event.UserEventData

data class PasswordRecoveredEvent(
    override val entity: User
) : Event {

    override fun getData() = UserEventData(
        username = entity.username,
        password = entity.password
    )
}
