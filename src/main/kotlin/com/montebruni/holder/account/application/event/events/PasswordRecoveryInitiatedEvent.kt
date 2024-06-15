package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.event.PasswordRecoveryInitiatedEventData

data class PasswordRecoveryInitiatedEvent(
    override val entity: User
) : Event {

    override fun getData() = PasswordRecoveryInitiatedEventData(
        username = entity.username,
        passwordRecoverToken = entity.passwordRecoverToken
            ?: throw IllegalArgumentException("Password recover token is null"),
        passwordRecoverTokenExpiration = entity.passwordRecoverTokenExpiration
            ?: throw IllegalArgumentException("Password recover token expiration is null")
    )
}
