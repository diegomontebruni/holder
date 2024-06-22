package com.montebruni.holder.fixtures

import com.montebruni.holder.asset.domain.entity.Asset
import com.montebruni.holder.asset.domain.valueobject.Amount
import com.montebruni.holder.asset.infrastructure.database.postgres.model.AssetPostgresModel
import java.util.UUID

fun createAsset() = Asset(
    walletId = UUID.randomUUID(),
    ticker = "AAPL",
    quantity = 10,
    averagePrice = Amount(100.0)
)

fun createAssetPostgresModel() = AssetPostgresModel(
    walletId = UUID.randomUUID(),
    ticker = "AAPL",
    quantity = 10,
    averagePrice = 100.0
)
