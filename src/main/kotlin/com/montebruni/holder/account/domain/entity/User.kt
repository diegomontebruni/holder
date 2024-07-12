package com.montebruni.holder.account.domain.entity

import com.montebruni.holder.account.domain.constants.PASSWORD_RECOVER_TOKEN_EXPIRATION_TIME
import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import com.montebruni.holder.account.domain.exception.UserAlreadyRegisteredException
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.PasswordRecoverToken
import com.montebruni.holder.account.domain.valueobject.Username
import java.time.Instant
import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val username: Username,
    val password: Password,
    val status: Status = Status.PENDING,
    val roles: Set<Role> = emptySet(),
    val passwordRecoverToken: PasswordRecoverToken? = null,
    val passwordRecoverTokenExpiration: Instant? = null,
) {

    constructor(username: String) : this(username = Username(username), password = Password())

    fun isPending() = status == Status.PENDING

    fun isActive() = status == Status.ACTIVE

    fun canBeRegistered() = isPending().takeUnless { it.not() } ?: throw UserAlreadyRegisteredException()

    fun activate() = copy(status = Status.ACTIVE)

    fun generatePasswordRecoverToken(encryptorProvider: EncryptorProvider? = null) = copy(
        passwordRecoverToken = PasswordRecoverToken.generateRandomToken(encryptorProvider),
        passwordRecoverTokenExpiration = Instant.now().plusSeconds(PASSWORD_RECOVER_TOKEN_EXPIRATION_TIME)
    )

    fun canInitiateRecoverPassword(): Boolean =
        isActive() &&
            (
                passwordRecoverToken == null ||
                    Instant.now().isAfter(passwordRecoverTokenExpiration)
                )

    fun canRecoverPassword(): Boolean =
        isActive() &&
            passwordRecoverToken != null &&
            Instant.now().isBefore(passwordRecoverTokenExpiration)
}
