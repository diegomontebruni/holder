package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.account.application.usecase.input.InitiatePasswordRecoveryInput
import com.montebruni.holder.account.domain.valueobject.Username
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Initiate Password Recover Request")
data class InitiatePasswordRecoverRequest(
    @Schema(description = "Username", example = "john.doe@doe.com")
    val username: String
)

fun InitiatePasswordRecoverRequest.toInput() = InitiatePasswordRecoveryInput(
    username = Username(username)
)
