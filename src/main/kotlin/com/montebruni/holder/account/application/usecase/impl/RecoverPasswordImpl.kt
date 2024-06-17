package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.PasswordRecoveredEvent
import com.montebruni.holder.account.application.usecase.RecoverPassword
import com.montebruni.holder.account.application.usecase.input.RecoverPasswordInput
import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import com.montebruni.holder.account.domain.exception.InvalidPasswordTokenRecoverException
import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.account.domain.valueobject.Password
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RecoverPasswordImpl(
    private val userRepository: UserRepository,
    private val encryptorProvider: EncryptorProvider,
    private val eventPublisher: EventPublisher
) : RecoverPassword {

    override fun execute(input: RecoverPasswordInput) {
        val user = userRepository.findByPasswordRecoverToken(input.token)
            ?.takeIf { it.canRecoverPassword() }
            ?: throw InvalidPasswordTokenRecoverException()

        user
            .copy(password = Password(), passwordRecoverTokenExpiration = Instant.now())
            .also { it.copy(password = it.password.encrypt(encryptorProvider)).let(userRepository::save) }
            .let(::PasswordRecoveredEvent)
            .let(eventPublisher::publishEvent)
    }
}
