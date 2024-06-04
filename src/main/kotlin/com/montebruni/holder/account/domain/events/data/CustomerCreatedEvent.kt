package com.montebruni.holder.account.domain.events.data

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.common.event.Event
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