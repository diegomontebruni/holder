package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.CustomerRegistrationCompletedEvent
import com.montebruni.holder.account.application.usecase.CompleteCustomerRegistration
import com.montebruni.holder.account.application.usecase.input.CompleteCustomerRegistrationInput
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class CompleteCustomerRegistrationImpl(
    private val userRepository: UserRepository,
    private val eventPublisher: EventPublisher
) : CompleteCustomerRegistration {

    override fun execute(input: CompleteCustomerRegistrationInput) {
        val user = userRepository.findById(input.userId) ?: throw UserNotFoundException()

        CustomerRegistrationCompletedEvent(entity = user, name = input.name)
            .let(eventPublisher::publishEvent)
    }
}
