package com.montebruni.holder.fixtures

import com.montebruni.holder.wallet.application.domain.entity.Wallet
import com.montebruni.holder.wallet.application.domain.valueobject.Amount
import com.montebruni.holder.wallet.application.usecase.input.CreateWalletInput
import com.montebruni.holder.wallet.infrastructure.database.postgres.model.WalletPostgresModel
import java.util.UUID

fun createWalletModel() = WalletPostgresModel(
    id = UUID.randomUUID(),
    managerId = UUID.randomUUID(),
    balance = 100.0
)

fun createWallet() = Wallet(
    id = UUID.randomUUID(),
    managerId = UUID.randomUUID(),
    balance = Amount(100.0)
)

fun createCreateWalletInput() = CreateWalletInput(
    customerId = UUID.randomUUID(),
    managerId = UUID.randomUUID(),
)
