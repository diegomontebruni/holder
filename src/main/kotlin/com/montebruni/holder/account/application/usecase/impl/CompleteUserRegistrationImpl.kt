package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.usecase.CompleteUserRegistration
import com.montebruni.holder.account.application.usecase.input.CompleteUserRegistrationInput
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class CompleteUserRegistrationImpl(
    private val userRepository: UserRepository,
) : CompleteUserRegistration {

    override fun execute(input: CompleteUserRegistrationInput) {
        val user = userRepository.findById(input.userId)
            ?.takeIf { it.canBeRegistered() }
            ?: throw UserNotFoundException()

        user
            .activate()
            .let(userRepository::save)
    }
}
