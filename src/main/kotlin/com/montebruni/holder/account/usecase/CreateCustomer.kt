package com.montebruni.holder.account.usecase

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.port.CustomerRepository
import com.montebruni.holder.account.domain.port.UserRepository
import com.montebruni.holder.account.events.customer.CustomerCreatedEvent
import com.montebruni.holder.account.usecase.input.CreateCustomerInput
import com.montebruni.holder.account.usecase.input.toCustomer
import com.montebruni.holder.account.usecase.output.CreateCustomerOutput
import com.montebruni.holder.account.usecase.output.fromCustomer
import com.montebruni.holder.common.event.EventPublisher
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateCustomer(
    private val userRepository: UserRepository,
    private val customerRepository: CustomerRepository,
    private val eventPublisher: EventPublisher
) {

    fun execute(input: CreateCustomerInput): CreateCustomerOutput {
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
