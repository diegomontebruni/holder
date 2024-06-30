package com.montebruni.holder.asset.infrastructure.event.listener

import com.montebruni.holder.asset.application.usecase.UpdateStockAsset
import com.montebruni.holder.asset.application.usecase.request.UpdateStockAssetInput
import com.montebruni.holder.asset.domain.entity.Operation
import com.montebruni.holder.asset.domain.entity.TransactionStatus
import com.montebruni.holder.asset.domain.valueobject.Amount
import com.montebruni.holder.transaction.application.event.events.TransactionCreatedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UpdateStockAssetListener(
    private val updateStockAsset: UpdateStockAsset
) {

    private val acceptedTypes = listOf("STOCK")

    @EventListener(classes = [TransactionCreatedEvent::class])
    fun handle(event: TransactionCreatedEvent) {
        val eventData = event.getData()

        if (eventData.type!!.name !in acceptedTypes) return

        UpdateStockAssetInput(
            transactionId = eventData.id!!,
            transactionStatus = TransactionStatus.valueOf(eventData.status!!.name),
            walletId = eventData.walletId!!,
            ticker = eventData.ticker!!,
            quantity = eventData.quantity!!,
            value = Amount(eventData.value!!.value),
            operation = Operation.valueOf(eventData.operation!!.name)
        ).let(updateStockAsset::execute)
    }
}
