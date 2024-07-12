package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.usecase.Login
import com.montebruni.holder.account.application.usecase.input.LoginInput
import com.montebruni.holder.account.application.usecase.output.LoginOutput
import com.montebruni.holder.account.application.usecase.output.from
import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import com.montebruni.holder.account.domain.crypto.TokenProvider
import com.montebruni.holder.account.domain.crypto.data.TokenConfiguration
import com.montebruni.holder.account.domain.exception.InvalidCredentialsException
import com.montebruni.holder.account.domain.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class LoginImpl(
    private val userRepository: UserRepository,
    private val encryptorProvider: EncryptorProvider,
    private val tokenProvider: TokenProvider
) : Login {

    override fun execute(input: LoginInput): LoginOutput {
        val user = userRepository.findByUsername(input.username.value) ?: throw InvalidCredentialsException()

        input
            .password
            .validate(user.password.value, encryptorProvider)
            .takeIf { it }
            ?: throw InvalidCredentialsException()

        return TokenConfiguration(
            subject = user.id.toString(),
            roles = user.roles.map { it.name }
        )
            .let(tokenProvider::encode)
            .let(LoginOutput::from)
    }
}
