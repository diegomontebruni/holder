package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.event.CustomerEventData

class CustomerRegistrationCompletedEvent(
    override val entity: Customer
) : Event {

    override fun getData(): CustomerEventData = CustomerEventData(
        id = entity.id,
        userId = entity.userId,
        name = entity.name,
    )
}
