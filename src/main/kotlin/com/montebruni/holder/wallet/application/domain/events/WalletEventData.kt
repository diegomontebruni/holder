package com.montebruni.holder.wallet.application.domain.events

import com.montebruni.holder.common.event.EventData
import com.montebruni.holder.wallet.application.domain.valueobject.Amount
import java.time.Instant
import java.util.UUID

data class WalletEventData(
    val id: UUID? = null,
    val managerId: UUID? = null,
    val balance: Amount? = null,
    val createdAt: Instant? = null
) : EventData
