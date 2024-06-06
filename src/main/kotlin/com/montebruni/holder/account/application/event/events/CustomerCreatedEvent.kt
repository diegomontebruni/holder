package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.event.CustomerEventData
import java.util.UUID

data class CustomerCreatedEvent(
    override val entity: Customer,
    private val managerId: UUID? = null
) : Event {

    override fun getData(): CustomerEventData = CustomerEventData(
        id = entity.id,
        userId = entity.userId,
        name = entity.name,
        email = entity.email,
        createdAt = entity.createdAt,
        managerId = managerId
    )
}
