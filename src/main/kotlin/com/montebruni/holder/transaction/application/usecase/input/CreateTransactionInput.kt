package com.montebruni.holder.transaction.application.usecase.input

import com.montebruni.holder.transaction.domain.entity.Operation
import com.montebruni.holder.transaction.domain.entity.Transaction
import com.montebruni.holder.transaction.domain.entity.Type
import com.montebruni.holder.transaction.domain.valueobject.Amount
import java.util.UUID

data class CreateTransactionInput(
    val walletId: UUID,
    val ticker: String,
    val quantity: Int,
    val value: Amount,
    val operation: Operation,
    val type: Type,
    val description: String? = null
)

fun CreateTransactionInput.toTransaction() = Transaction(
    walletId = walletId,
    ticker = ticker,
    quantity = quantity,
    value = value,
    operation = operation,
    type = type,
    description = description
)
