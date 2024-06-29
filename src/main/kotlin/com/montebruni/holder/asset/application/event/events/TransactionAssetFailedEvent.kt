package com.montebruni.holder.asset.application.event.events

import com.montebruni.holder.asset.domain.event.TransactionAssetFailedEventData

data class TransactionAssetFailedEvent(
    override val data: TransactionAssetFailedEventData
) : Event
