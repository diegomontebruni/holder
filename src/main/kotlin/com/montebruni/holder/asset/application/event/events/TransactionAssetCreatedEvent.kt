package com.montebruni.holder.asset.application.event.events

import com.montebruni.holder.asset.domain.event.TransactionAssetCreatedEventData

data class TransactionAssetCreatedEvent(
    override val data: TransactionAssetCreatedEventData
) : Event
