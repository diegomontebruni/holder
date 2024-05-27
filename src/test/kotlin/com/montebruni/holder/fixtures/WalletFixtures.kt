package com.montebruni.holder.fixtures

import com.montebruni.holder.common.valueobject.Amount
import com.montebruni.holder.wallet.domain.entity.Wallet
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