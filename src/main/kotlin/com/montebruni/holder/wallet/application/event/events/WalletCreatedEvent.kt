package com.montebruni.holder.wallet.application.event.events

import com.montebruni.holder.wallet.domain.entity.Wallet
import com.montebruni.holder.wallet.domain.event.WalletEventData

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
