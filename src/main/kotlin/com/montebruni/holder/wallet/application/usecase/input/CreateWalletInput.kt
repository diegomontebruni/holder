package com.montebruni.holder.wallet.application.usecase.input

import com.montebruni.holder.wallet.domain.entity.Wallet
import java.util.UUID

data class CreateWalletInput(
    val customerId: UUID,
    val managerId: UUID? = null
) {
    companion object
}

fun CreateWalletInput.toWallet() = Wallet(
    id = customerId,
    managerId = managerId ?: customerId
)
