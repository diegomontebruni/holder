package com.montebruni.holder.wallet.presentation.interfaces.response

import java.time.Instant
import java.util.UUID

data class GetWalletByIdResponse(
    val id: UUID,
    val managerId: UUID,
    val balance: Double,
    val createdAt: Instant
)
