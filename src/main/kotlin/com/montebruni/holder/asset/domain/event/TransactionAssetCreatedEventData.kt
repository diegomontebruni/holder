package com.montebruni.holder.asset.domain.event

import java.util.UUID

data class TransactionAssetCreatedEventData(
    val transactionId: UUID,
) : EventData
