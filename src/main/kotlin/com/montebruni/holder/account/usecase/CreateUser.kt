package com.montebruni.holder.account.usecase

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.exception.UserAlreadyExistsException
import com.montebruni.holder.account.domain.port.UserRepository
import com.montebruni.holder.account.events.user.UserCreatedEvent
import com.montebruni.holder.account.usecase.input.CreateUserInput
import com.montebruni.holder.account.usecase.input.toUser
import com.montebruni.holder.account.usecase.output.CreateUserOutput
import com.montebruni.holder.account.usecase.output.fromUser
import com.montebruni.holder.common.event.EventPublisher
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateUser(
    private val userRepository: UserRepository,
    private val eventPublisher: EventPublisher
) {

    fun execute(input: CreateUserInput): CreateUserOutput {
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
