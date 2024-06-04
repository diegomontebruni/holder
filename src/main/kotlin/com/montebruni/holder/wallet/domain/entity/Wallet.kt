package com.montebruni.holder.wallet.domain.entity

import com.montebruni.holder.wallet.domain.valueobject.Amount
import java.time.Instant
import java.util.UUID

data class Wallet(
    val id: UUID,
    val managerId: UUID,
    val balance: Amount,
    val createdAt: Instant? = null
)
