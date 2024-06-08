package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.CustomerRegistrationCompletedEvent
import com.montebruni.holder.account.application.usecase.CompleteCustomerRegistration
import com.montebruni.holder.account.application.usecase.input.CompleteCustomerRegistrationInput
import com.montebruni.holder.account.domain.exception.CustomerNotFoundException
import com.montebruni.holder.account.domain.exception.UserAlreadyRegisteredException
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.CustomerRepository
import com.montebruni.holder.account.domain.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class CompleteCustomerRegistrationImpl(
    private val userRepository: UserRepository,
    private val customerRepository: CustomerRepository,
    private val eventPublisher: EventPublisher
) : CompleteCustomerRegistration {

    override fun execute(input: CompleteCustomerRegistrationInput) {
        userRepository.findById(input.userId)
            ?.also { if (it.isPending().not()) throw UserAlreadyRegisteredException() }
            ?: throw UserNotFoundException()

        customerRepository.findByUserId(input.userId)
            ?.let { it.copy(name = input.name) }
            ?.let(::CustomerRegistrationCompletedEvent)
            ?.also(eventPublisher::publishEvent)
            ?: throw CustomerNotFoundException()
    }
}
