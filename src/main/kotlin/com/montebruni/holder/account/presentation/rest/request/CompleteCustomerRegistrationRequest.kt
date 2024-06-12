package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.account.application.usecase.input.CompleteCustomerRegistrationInput
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "Complete customer registration request")
data class CompleteCustomerRegistrationRequest(
    @Schema(
        description = "The user id of the customer",
        example = "123e4567-e89b-12d3-a456-426614174000",
        required = true
    )
    val userId: UUID,
    @Schema(
        description = "The name of the customer",
        example = "John Snow",
        required = true
    )
    val name: String,
)

fun CompleteCustomerRegistrationRequest.toCompleteCustomerRegistrationInput() = CompleteCustomerRegistrationInput(
    userId = userId,
    name = name,
)
