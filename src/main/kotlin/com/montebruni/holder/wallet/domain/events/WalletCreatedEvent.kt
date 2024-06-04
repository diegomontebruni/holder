package com.montebruni.holder.wallet.domain.events

import com.montebruni.holder.common.event.Event
import com.montebruni.holder.wallet.domain.entity.Wallet

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
