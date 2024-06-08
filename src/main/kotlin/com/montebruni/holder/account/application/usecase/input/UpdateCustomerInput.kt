package com.montebruni.holder.account.application.usecase.input

import java.util.UUID

data class UpdateCustomerInput(
    val id: UUID,
    val name: String,
)
