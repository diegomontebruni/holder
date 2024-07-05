package com.montebruni.holder.fixtures

import com.montebruni.holder.stock.domain.entity.Stock
import com.montebruni.holder.stock.domain.valueobject.Amount
import com.montebruni.holder.stock.infrastructure.database.postgres.model.StockPostgresModel

fun createStock() = Stock(
    ticker = "BBAS3",
    price = Amount(27.55),
    name = "Banco do Brasil"
)

fun createStockPostgresModel() = StockPostgresModel(
    ticker = "BBAS3",
    price = 27.55,
    name = "Banco do Brasil"
)
