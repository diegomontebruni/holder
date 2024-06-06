package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.CustomerCreatedEvent
import com.montebruni.holder.account.application.usecase.CreateCustomer
import com.montebruni.holder.account.application.usecase.input.CreateCustomerInput
import com.montebruni.holder.account.application.usecase.input.toCustomer
import com.montebruni.holder.account.application.usecase.output.CreateCustomerOutput
import com.montebruni.holder.account.application.usecase.output.fromCustomer
import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.CustomerRepository
import com.montebruni.holder.account.domain.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.also
import kotlin.let

@Service
class CreateCustomerImpl(
    private val userRepository: UserRepository,
    private val customerRepository: CustomerRepository,
    private val eventPublisher: EventPublisher
) : CreateCustomer {

    override fun execute(input: CreateCustomerInput): CreateCustomerOutput {
        userRepository.findById(input.userId) ?: throw UserNotFoundException()

        return input
            .toCustomer()
            .let(customerRepository::save)
            .also { publishCustomerCreatedEvent(it, input.managerId) }
            .let(CreateCustomerOutput::fromCustomer)
    }

    private fun publishCustomerCreatedEvent(customer: Customer, managerId: UUID? = null) =
        CustomerCreatedEvent(customer, managerId).let(eventPublisher::publishEvent)
}
