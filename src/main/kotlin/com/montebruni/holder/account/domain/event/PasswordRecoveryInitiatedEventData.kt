package com.montebruni.holder.account.domain.event

import com.montebruni.holder.account.domain.valueobject.PasswordRecoverToken
import com.montebruni.holder.account.domain.valueobject.Username
import java.time.Instant

data class PasswordRecoveryInitiatedEventData(
    val username: Username,
    val passwordRecoverToken: PasswordRecoverToken,
    val passwordRecoverTokenExpiration: Instant
) : EventData
