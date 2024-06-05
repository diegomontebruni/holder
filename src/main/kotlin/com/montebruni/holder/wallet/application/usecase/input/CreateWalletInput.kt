package com.montebruni.holder.wallet.application.usecase.input

import com.montebruni.holder.account.application.domain.events.CustomerCreatedEvent
import com.montebruni.holder.wallet.application.domain.entity.Wallet
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

fun CreateWalletInput.Companion.fromEvent(event: CustomerCreatedEvent) = CreateWalletInput(
    customerId = event.getData().id!!,
    managerId = event.getData().managerId
)
