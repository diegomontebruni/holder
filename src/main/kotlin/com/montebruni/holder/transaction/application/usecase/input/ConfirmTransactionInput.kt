package com.montebruni.holder.transaction.application.usecase.input

import java.util.UUID

data class ConfirmTransactionInput(
    val transactionId: UUID
)
