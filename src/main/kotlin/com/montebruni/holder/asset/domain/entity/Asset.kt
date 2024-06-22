package com.montebruni.holder.asset.domain.entity

import com.montebruni.holder.asset.domain.valueobject.Amount
import java.util.UUID

data class Asset(
    val walletId: UUID,
    val ticker: String,
    val quantity: Int,
    val averagePrice: Amount,
)
