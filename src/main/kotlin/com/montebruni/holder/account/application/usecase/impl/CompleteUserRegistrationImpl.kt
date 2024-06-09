package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.UserRegistrationCompletedEvent
import com.montebruni.holder.account.application.usecase.CompleteUserRegistration
import com.montebruni.holder.account.application.usecase.input.CompleteUserRegistrationInput
import com.montebruni.holder.account.domain.exception.CustomerNotFoundException
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.CustomerRepository
import com.montebruni.holder.account.domain.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class CompleteUserRegistrationImpl(
    private val userRepository: UserRepository,
    private val customerRepository: CustomerRepository,
    private val eventPublisher: EventPublisher
) : CompleteUserRegistration {

    override fun execute(input: CompleteUserRegistrationInput) {
        val user = userRepository.findById(input.userId)
            ?.takeIf { it.canBeRegistered() }
            ?: throw UserNotFoundException()

        val customer = customerRepository.findByUserId(user.id) ?: throw CustomerNotFoundException()

        user
            .activate()
            .let(userRepository::save)
            .let { UserRegistrationCompletedEvent(it, customer) }
            .also(eventPublisher::publishEvent)
    }
}
