package com.montebruni.holder.transaction.domain.entity

import com.montebruni.holder.transaction.domain.valueobject.Amount
import java.time.Instant
import java.util.UUID

data class Transaction(
    val id: UUID = UUID.randomUUID(),
    val status: Status = Status.PENDING,
    val walletId: UUID,
    val ticker: String,
    val quantity: Int,
    val value: Amount,
    val operation: Operation,
    val type: Type,
    val description: String? = null,
    val createdAt: Instant? = null
) {

    fun toConfirmed(): Transaction? = status.takeIf { it == Status.PENDING }?.let { copy(status = Status.CONFIRMED) }

    fun toFailed(): Transaction? = status.takeIf { it == Status.PENDING }?.let { copy(status = Status.FAILED) }
}
