package com.montebruni.holder.fixtures

import com.montebruni.holder.stock.domain.entity.Stock
import com.montebruni.holder.stock.domain.valueobject.Amount
import com.montebruni.holder.stock.infrastructure.database.postgres.model.StockPostgresModel
import java.util.UUID

fun createStock() = Stock(
    symbol = "BBAS3",
    price = Amount(27.55),
)

fun createStockPostgresModel() = StockPostgresModel(
    id = UUID.randomUUID(),
    symbol = "BBAS3",
    price = 27.55,
)
