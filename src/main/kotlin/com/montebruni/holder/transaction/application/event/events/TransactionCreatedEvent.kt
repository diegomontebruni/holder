package com.montebruni.holder.transaction.application.event.events

import com.montebruni.holder.transaction.domain.entity.Transaction
import com.montebruni.holder.transaction.domain.event.TransactionEventData

data class TransactionCreatedEvent(
    override val entity: Transaction
) : Event {

    override fun getData(): TransactionEventData = TransactionEventData(
        id = entity.id,
        status = entity.status,
        walletId = entity.walletId,
        ticker = entity.ticker,
        quantity = entity.quantity,
        value = entity.value,
        operation = entity.operation,
        type = entity.type,
        description = entity.description,
        createdAt = entity.createdAt
    )
}
