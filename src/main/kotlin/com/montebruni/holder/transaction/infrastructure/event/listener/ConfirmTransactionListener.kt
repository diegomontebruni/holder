package com.montebruni.holder.transaction.infrastructure.event.listener

import com.montebruni.holder.asset.application.event.events.TransactionAssetCreatedEvent
import com.montebruni.holder.transaction.application.usecase.ConfirmTransaction
import com.montebruni.holder.transaction.application.usecase.input.ConfirmTransactionInput
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ConfirmTransactionListener(
    private val confirmTransaction: ConfirmTransaction
) {

    @EventListener(classes = [TransactionAssetCreatedEvent::class])
    fun handle(event: TransactionAssetCreatedEvent) {
        ConfirmTransactionInput(transactionId = event.data.transactionId)
            .let(confirmTransaction::execute)
    }
}
