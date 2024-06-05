package com.montebruni.holder.wallet.usecase.output

import com.montebruni.holder.wallet.domain.entity.Wallet
import com.montebruni.holder.wallet.domain.valueobject.Amount
import java.time.Instant
import java.util.UUID

data class CreateWalletOutput(
    val id: UUID,
    val managerId: UUID,
    val balance: Amount,
    val createdAt: Instant
) {
    companion object
}

fun CreateWalletOutput.Companion.fromWallet(wallet: Wallet) = CreateWalletOutput(
    id = wallet.id,
    managerId = wallet.managerId,
    balance = wallet.balance,
    createdAt = wallet.createdAt ?: throw IllegalArgumentException("Wallet must have a creation date")
)
