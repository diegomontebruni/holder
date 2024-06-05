package com.montebruni.holder.account.application.usecase.input

import com.montebruni.holder.account.application.domain.entity.Customer
import com.montebruni.holder.account.application.domain.events.UserCreatedEvent
import com.montebruni.holder.account.application.domain.valueobject.Email
import java.util.UUID

data class CreateCustomerInput(
    val userId: UUID,
    val email: Email,
    val managerId: UUID? = null
) {
    companion object
}

fun CreateCustomerInput.toCustomer() = Customer(
    userId = userId,
    email = email
)

fun CreateCustomerInput.Companion.fromEvent(event: UserCreatedEvent) = CreateCustomerInput(
    userId = event.getData().id!!,
    email = event.getData().username!!.value.let(::Email),
    managerId = event.getData().managerId
)
