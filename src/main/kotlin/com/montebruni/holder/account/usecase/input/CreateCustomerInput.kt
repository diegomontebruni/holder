package com.montebruni.holder.account.usecase.input

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.valueobject.Email
import java.util.UUID

data class CreateCustomerInput(
    val userId: UUID,
    val email: Email,
    val managerId: UUID? = null
)

fun CreateCustomerInput.toCustomer() = Customer(
    userId = userId,
    email = email
)
