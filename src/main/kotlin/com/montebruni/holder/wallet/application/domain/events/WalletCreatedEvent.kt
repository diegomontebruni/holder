package com.montebruni.holder.wallet.application.domain.events

import com.montebruni.holder.common.event.Event
import com.montebruni.holder.wallet.application.domain.entity.Wallet

data class WalletCreatedEvent(
    override val entity: Wallet,
) : Event {

    override fun getData(): WalletEventData = WalletEventData(
        id = entity.id,
        managerId = entity.managerId,
        balance = entity.balance,
        createdAt = entity.createdAt
    )
}
