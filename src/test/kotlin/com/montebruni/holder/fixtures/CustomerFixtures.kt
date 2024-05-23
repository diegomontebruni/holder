package com.montebruni.holder.fixtures

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.infrastructure.database.postgres.model.CustomerPostgresModel
import java.util.UUID

fun createCustomer() = Customer(
    id = UUID.randomUUID(),
    userId = UUID.randomUUID(),
    name = "John Doe",
    email = "john.doe@gmail.com"
)

fun createCustomerModel() = CustomerPostgresModel(
    id = UUID.randomUUID(),
    userId = UUID.randomUUID(),
    name = "John Doe",
    email = "john.doe@gmail.com"
)
