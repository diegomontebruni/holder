package com.montebruni.holder.transaction.application.usecase.output

import com.montebruni.holder.transaction.domain.entity.Operation
import com.montebruni.holder.transaction.domain.entity.Status
import com.montebruni.holder.transaction.domain.entity.Type
import com.montebruni.holder.transaction.domain.valueobject.Amount
import java.time.Instant
import java.util.UUID

data class FindTransactionByIdOutput(
    val id: UUID,
    val status: Status,
    val walletId: UUID,
    val ticker: String,
    val quantity: Int,
    val value: Amount,
    val operation: Operation,
    val type: Type,
    val description: String?,
    val createdAt: Instant?
)
