package com.montebruni.holder.fixtures

import com.montebruni.holder.account.application.event.events.CustomerCreatedEvent
import com.montebruni.holder.account.application.event.events.CustomerRegistrationCompletedEvent
import com.montebruni.holder.account.application.usecase.input.CompleteCustomerRegistrationInput
import com.montebruni.holder.account.application.usecase.input.CreateCustomerInput
import com.montebruni.holder.account.application.usecase.input.UpdateCustomerInput
import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.valueobject.Email
import com.montebruni.holder.account.infrastructure.database.postgres.model.CustomerPostgresModel
import java.util.UUID

fun createCustomer() = Customer(
    id = UUID.randomUUID(),
    userId = UUID.randomUUID(),
    name = "John Doe",
    email = Email("john.doe@gmail.com")
)

fun createCustomerModel() = CustomerPostgresModel(
    id = UUID.randomUUID(),
    userId = UUID.randomUUID(),
    name = "John Doe",
    email = "john.doe@gmail.com"
)

fun createCreateCustomerInput() = CreateCustomerInput(
    userId = UUID.randomUUID(),
    email = Email("john.doe@gmail.com"),
    managerId = UUID.randomUUID()
)

fun createCustomerCreatedEvent() = CustomerCreatedEvent(
    entity = createCustomer(),
    managerId = UUID.randomUUID()
)

fun createCompleteCustomerRegistrationInput() = CompleteCustomerRegistrationInput(
    userId = UUID.randomUUID(),
    name = "John Doe Input"
)

fun createUpdateCustomerInput() = UpdateCustomerInput(
    id = UUID.randomUUID(),
    name = "John Doe Input"
)

fun createCustomerRegistrationCompletedEvent() = CustomerRegistrationCompletedEvent(
    entity = createCustomer()
)
