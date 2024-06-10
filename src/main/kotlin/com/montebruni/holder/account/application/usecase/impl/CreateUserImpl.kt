package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.UserCreatedEvent
import com.montebruni.holder.account.application.usecase.CreateUser
import com.montebruni.holder.account.application.usecase.input.CreateUserInput
import com.montebruni.holder.account.application.usecase.output.CreateUserOutput
import com.montebruni.holder.account.application.usecase.output.fromUser
import com.montebruni.holder.account.domain.crypto.PasswordEncryptor
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.exception.UserAlreadyExistsException
import com.montebruni.holder.account.domain.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.also
import kotlin.let

@Service
class CreateUserImpl(
    private val userRepository: UserRepository,
    private val eventPublisher: EventPublisher,
    private val passwordEncryptor: PasswordEncryptor
) : CreateUser {

    override fun execute(input: CreateUserInput): CreateUserOutput {
        val username = input.username.value

        userRepository.findByUsername(username)?.let { throw UserAlreadyExistsException() }

        return User(username = username)
            .also { it.copy(password = it.password.encrypt(passwordEncryptor)).let(userRepository::save) }
            .also { publishUserCreatedEvent(it, input.managerId) }
            .let(CreateUserOutput::fromUser)
    }

    private fun publishUserCreatedEvent(user: User, managerId: UUID? = null) =
        UserCreatedEvent(user, managerId).let(eventPublisher::publishEvent)
}
