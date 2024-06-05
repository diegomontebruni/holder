package com.montebruni.holder.wallet.usecase.input

import com.montebruni.holder.wallet.domain.entity.Wallet
import java.util.UUID

data class CreateWalletInput(
    val customerId: UUID,
    val managerId: UUID? = null
)

fun CreateWalletInput.toWallet() = Wallet(
    id = customerId,
    managerId = managerId ?: customerId
)
