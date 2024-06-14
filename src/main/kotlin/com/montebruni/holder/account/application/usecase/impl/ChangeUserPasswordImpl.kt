package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.usecase.ChangeUserPassword
import com.montebruni.holder.account.application.usecase.input.ChangeUserPasswordInput
import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import com.montebruni.holder.account.domain.exception.InvalidUserPasswordException
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class ChangeUserPasswordImpl(
    private val userRepository: UserRepository,
    private val encryptorProvider: EncryptorProvider
) : ChangeUserPassword {

    override fun execute(input: ChangeUserPasswordInput) {
        val user = userRepository.findByUsername(input.username.value) ?: throw UserNotFoundException()

        input.oldPassword.validate(user.password.value, encryptorProvider)
            .takeIf { it }
            ?: throw InvalidUserPasswordException()

        user
            .copy(password = input.newPassword.encrypt(encryptorProvider))
            .let(userRepository::save)
    }
}
