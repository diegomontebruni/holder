package com.montebruni.holder.transaction.application.usecase.input

import java.util.UUID

data class FailTransactionInput(
    val transactionId: UUID
)
