package com.montebruni.holder.account.usecase.output

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.valueobject.Email
import java.time.Instant
import java.util.UUID

data class CreateCustomerOutput(
    val id: UUID,
    val userId: UUID,
    val email: Email,
    val createdAt: Instant
) {
    companion object
}

fun CreateCustomerOutput.Companion.fromCustomer(customer: Customer) = CreateCustomerOutput(
    id = customer.id,
    userId = customer.userId,
    email = customer.email,
    createdAt = customer.createdAt ?: throw IllegalArgumentException("Customer must have a creation date")
)
