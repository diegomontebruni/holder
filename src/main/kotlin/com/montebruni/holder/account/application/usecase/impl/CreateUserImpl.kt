package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.domain.entity.User
import com.montebruni.holder.account.application.domain.events.UserCreatedEvent
import com.montebruni.holder.account.application.domain.exception.UserAlreadyExistsException
import com.montebruni.holder.account.application.domain.port.UserRepository
import com.montebruni.holder.account.application.usecase.CreateUser
import com.montebruni.holder.account.application.usecase.input.CreateUserInput
import com.montebruni.holder.account.application.usecase.input.toUser
import com.montebruni.holder.account.application.usecase.output.CreateUserOutput
import com.montebruni.holder.account.application.usecase.output.fromUser
import com.montebruni.holder.common.event.EventPublisher
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.also
import kotlin.let

@Service
class CreateUserImpl(
    private val userRepository: UserRepository,
    private val eventPublisher: EventPublisher
) : CreateUser {

    override fun execute(input: CreateUserInput): CreateUserOutput {
        userRepository.findByUsername(input.username)?.let { throw UserAlreadyExistsException() }

        return input
            .toUser()
            .let(userRepository::save)
            .also { publishUserCreatedEvent(it, input.managerId) }
            .let(CreateUserOutput::fromUser)
    }

    private fun publishUserCreatedEvent(user: User, managerId: UUID? = null) =
        UserCreatedEvent(user, managerId).let(eventPublisher::publishEvent)
}