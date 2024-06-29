package com.montebruni.holder.asset.domain.event

import java.util.UUID

data class TransactionAssetFailedEventData(
    val transactionId: UUID,
) : EventData
