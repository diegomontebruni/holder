package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.event.UserRegistrationCompletedEventData

data class UserRegistrationCompletedEvent(
    override val entity: User,
    private val customer: Customer
) : Event {

    override fun getData() = UserRegistrationCompletedEventData(
        userId = entity.id,
        username = entity.username,
        status = entity.status,
        name = customer.name!!,
        email = customer.email
    )
}
