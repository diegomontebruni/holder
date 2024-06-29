package com.montebruni.holder.fixtures

import com.montebruni.holder.asset.application.usecase.request.UpdateStockAssetInput
import com.montebruni.holder.asset.domain.entity.Asset
import com.montebruni.holder.asset.domain.entity.Operation
import com.montebruni.holder.asset.domain.valueobject.Amount
import com.montebruni.holder.asset.infrastructure.database.postgres.model.AssetPostgresModel
import java.util.UUID

fun createAsset() = Asset(
    walletId = UUID.randomUUID(),
    ticker = "AAPL",
    quantity = 10,
    totalPaid = Amount(1000.0),
)

fun createAssetPostgresModel() = AssetPostgresModel(
    walletId = UUID.randomUUID(),
    ticker = "AAPL",
    quantity = 10,
    totalPaid = 1000.0,
    averagePrice = 100.0
)

fun createUpdateStockAssetInput() = UpdateStockAssetInput(
    transactionId = UUID.randomUUID(),
    walletId = UUID.randomUUID(),
    ticker = "AAPL",
    quantity = 10,
    value = Amount(1000.0),
    operation = Operation.CREDIT
)
