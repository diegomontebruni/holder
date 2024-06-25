package com.montebruni.holder.transaction.domain.event

import com.montebruni.holder.transaction.domain.entity.Operation
import com.montebruni.holder.transaction.domain.entity.Status
import com.montebruni.holder.transaction.domain.entity.Type
import com.montebruni.holder.transaction.domain.valueobject.Amount
import java.time.Instant
import java.util.UUID

data class TransactionEventData(
    val id: UUID? = null,
    val status: Status? = null,
    val walletId: UUID? = null,
    val ticker: String? = null,
    val quantity: Int? = null,
    val value: Amount? = null,
    val operation: Operation? = null,
    val type: Type? = null,
    val description: String? = null,
    val createdAt: Instant? = null
) : EventData
