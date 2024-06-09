package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.account.application.usecase.input.CreateUserInput
import com.montebruni.holder.account.domain.valueobject.Username
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "create a user request")
data class CreateUserRequest(
    @Schema(
        description = "The username of the user. Must be a email",
        example = "john.snow@winterfell.north",
        required = true
    )
    val username: String
)

fun CreateUserRequest.toCreateUserInput() = CreateUserInput(
    username = Username(username)
)
