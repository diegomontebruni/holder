package com.montebruni.holder.transaction.infrastructure.event.listener

import com.montebruni.holder.asset.application.event.events.TransactionAssetFailedEvent
import com.montebruni.holder.transaction.application.usecase.FailTransaction
import com.montebruni.holder.transaction.application.usecase.input.FailTransactionInput
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class FailTransactionListener(
    private val failTransaction: FailTransaction
) {

    @EventListener(classes = [TransactionAssetFailedEvent::class])
    fun handle(event: TransactionAssetFailedEvent) {
        FailTransactionInput(transactionId = event.data.transactionId)
            .let(failTransaction::execute)
    }
}
