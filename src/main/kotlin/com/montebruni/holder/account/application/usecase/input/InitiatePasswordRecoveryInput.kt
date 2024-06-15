package com.montebruni.holder.account.application.usecase.input

import com.montebruni.holder.account.domain.valueobject.Username

data class InitiatePasswordRecoveryInput(
    val username: Username
)
