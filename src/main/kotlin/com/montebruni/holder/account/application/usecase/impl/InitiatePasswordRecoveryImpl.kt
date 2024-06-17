package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.PasswordRecoveryInitiatedEvent
import com.montebruni.holder.account.application.usecase.InitiatePasswordRecovery
import com.montebruni.holder.account.application.usecase.input.InitiatePasswordRecoveryInput
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class InitiatePasswordRecoveryImpl(
    private val userRepository: UserRepository,
    private val eventPublisher: EventPublisher
) : InitiatePasswordRecovery {

    override fun execute(input: InitiatePasswordRecoveryInput) {
        val user = userRepository.findByUsername(input.username.value) ?: throw UserNotFoundException()

        user.canInitiateRecoverPassword()
            .takeIf { it.not() }
            ?.let { return }

        user
            .generatePasswordRecoverToken()
            .also(userRepository::save)
            .let(::PasswordRecoveryInitiatedEvent)
            .let(eventPublisher::publishEvent)
    }
}
