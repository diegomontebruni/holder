package com.montebruni.holder.account.application.usecase.input

import com.montebruni.holder.account.application.event.events.UserCreatedEvent
import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.valueobject.Email
import java.util.UUID

data class CreateCustomerInput(
    val userId: UUID,
    val email: Email
) {
    companion object
}

fun CreateCustomerInput.toCustomer() = Customer(
    userId = userId,
    email = email
)

fun CreateCustomerInput.Companion.fromEvent(event: UserCreatedEvent) = CreateCustomerInput(
    userId = event.getData().id!!,
    email = event.getData().username!!.value.let(::Email)
)
