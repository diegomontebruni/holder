package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.CustomerUpdatedEvent
import com.montebruni.holder.account.application.usecase.UpdateCustomer
import com.montebruni.holder.account.application.usecase.input.UpdateCustomerInput
import com.montebruni.holder.account.domain.exception.CustomerNotFoundException
import com.montebruni.holder.account.domain.repositories.CustomerRepository
import org.springframework.stereotype.Service

@Service
class UpdateCustomerImpl(
    private val customerRepository: CustomerRepository,
    private val eventPublisher: EventPublisher
) : UpdateCustomer {

    override fun execute(input: UpdateCustomerInput) {
        customerRepository.findById(input.id)
            ?.let { it.copy(name = input.name) }
            ?.let(customerRepository::save)
            ?.let(::CustomerUpdatedEvent)
            ?.also(eventPublisher::publishEvent)
            ?: throw CustomerNotFoundException()
    }
}
