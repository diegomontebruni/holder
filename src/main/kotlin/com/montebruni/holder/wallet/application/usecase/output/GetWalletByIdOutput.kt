package com.montebruni.holder.wallet.application.usecase.output

import com.montebruni.holder.wallet.domain.valueobject.Amount
import java.time.Instant
import java.util.UUID

data class GetWalletByIdOutput(
    val id: UUID,
    val managerId: UUID,
    val balance: Amount,
    val createdAt: Instant
)
