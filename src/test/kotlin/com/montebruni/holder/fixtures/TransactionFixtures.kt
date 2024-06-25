package com.montebruni.holder.fixtures

import com.montebruni.holder.transaction.domain.entity.Operation
import com.montebruni.holder.transaction.domain.entity.Transaction
import com.montebruni.holder.transaction.domain.entity.Type
import com.montebruni.holder.transaction.domain.valueobject.Amount
import com.montebruni.holder.transaction.infrastructure.database.postgres.model.TransactionPostgresModel
import java.util.UUID

fun createTransaction() = Transaction(
    id = UUID.randomUUID(),
    walletId = UUID.randomUUID(),
    ticker = "PETR4",
    quantity = 100,
    value = Amount(100.0),
    operation = Operation.CREDIT,
    type = Type.STOCK
)

fun createTransactionPostgresModel() = TransactionPostgresModel(
    status = "CONFIRMED",
    walletId = UUID.randomUUID(),
    ticker = "PETR4",
    quantity = 100,
    value = 100.0,
    operation = "CREDIT",
    type = "STOCK"
)
