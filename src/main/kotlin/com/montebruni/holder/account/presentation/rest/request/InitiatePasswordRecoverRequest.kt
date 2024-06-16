package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.account.application.usecase.input.InitiatePasswordRecoveryInput
import com.montebruni.holder.account.domain.valueobject.Username

data class InitiatePasswordRecoverRequest(
    val username: String
)

fun InitiatePasswordRecoverRequest.toInput() = InitiatePasswordRecoveryInput(
    username = Username(username)
)
