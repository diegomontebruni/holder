package com.montebruni.holder.asset.application.usecase.impl

import com.montebruni.holder.asset.application.client.TransactionClient
import com.montebruni.holder.asset.application.client.exception.TransactionNotFoundException
import com.montebruni.holder.asset.application.event.EventPublisher
import com.montebruni.holder.asset.application.event.events.TransactionAssetFailedEvent
import com.montebruni.holder.asset.application.usecase.UpdateStockAsset
import com.montebruni.holder.asset.application.usecase.request.UpdateStockAssetInput
import com.montebruni.holder.asset.domain.entity.Asset
import com.montebruni.holder.asset.domain.entity.TransactionStatus
import com.montebruni.holder.asset.domain.event.TransactionAssetFailedEventData
import com.montebruni.holder.asset.domain.repositories.AssetRepository
import org.springframework.stereotype.Service

@Service
class UpdateStockAssetImpl(
    private val assetRepository: AssetRepository,
    private val transactionClient: TransactionClient,
    private val eventPublisher: EventPublisher
) : UpdateStockAsset {

    override fun execute(input: UpdateStockAssetInput) {
        runCatching {
            val transaction = transactionClient.findById(input.transactionId)
                ?: throw TransactionNotFoundException()

            transaction
                .takeIf { it.status.name != TransactionStatus.PENDING.name }
                ?.let { return@execute }

            val asset = assetRepository
                .findByWalletIdAndTicker(input.walletId, input.ticker)
                ?.update(input.quantity, input.value, input.operation)
                ?: createAsset(input)

            assetRepository.save(asset)
        }.getOrElse {
            TransactionAssetFailedEventData(input.transactionId)
                .let(::TransactionAssetFailedEvent)
                .let(eventPublisher::publishEvent)
        }
    }

    private fun createAsset(input: UpdateStockAssetInput) = Asset(
        walletId = input.walletId,
        ticker = input.ticker,
        quantity = input.quantity,
        unitPrice = input.value
    )
}
