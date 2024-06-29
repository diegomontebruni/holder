package com.montebruni.holder.asset.application.usecase.request

import com.montebruni.holder.asset.domain.entity.Operation
import com.montebruni.holder.asset.domain.valueobject.Amount
import java.util.UUID

data class UpdateStockAssetInput(
    val transactionId: UUID,
    val walletId: UUID,
    val ticker: String,
    val quantity: Int,
    val value: Amount,
    val operation: Operation,
)
