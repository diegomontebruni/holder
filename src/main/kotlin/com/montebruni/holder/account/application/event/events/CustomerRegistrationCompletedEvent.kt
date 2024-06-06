package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.event.CustomerEventData

class CustomerRegistrationCompletedEvent(
    override val entity: User,
    val name: String
) : Event {

    override fun getData(): CustomerEventData = CustomerEventData(
        userId = entity.id,
        name = name,
    )
}
