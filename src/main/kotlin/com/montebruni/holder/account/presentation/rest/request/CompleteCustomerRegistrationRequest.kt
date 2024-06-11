package com.montebruni.holder.account.presentation.rest.request

import com.montebruni.holder.account.application.usecase.input.CompleteCustomerRegistrationInput
import java.util.UUID

data class CompleteCustomerRegistrationRequest(
    val userId: UUID,
    val name: String,
)

fun CompleteCustomerRegistrationRequest.toCompleteCustomerRegistrationInput() = CompleteCustomerRegistrationInput(
    userId = userId,
    name = name,
)
