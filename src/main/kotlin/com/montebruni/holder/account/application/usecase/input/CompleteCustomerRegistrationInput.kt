package com.montebruni.holder.account.application.usecase.input

import java.util.UUID

data class CompleteCustomerRegistrationInput(
    val userId: UUID,
    val name: String
)
