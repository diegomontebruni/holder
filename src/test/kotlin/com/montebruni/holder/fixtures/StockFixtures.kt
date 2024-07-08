package com.montebruni.holder.fixtures

import com.montebruni.holder.stock.application.provider.StockProviderResponse
import com.montebruni.holder.stock.application.usecase.input.GetStockInput
import com.montebruni.holder.stock.application.usecase.input.SaveStockInput
import com.montebruni.holder.stock.application.usecase.output.GetStockOutput
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

fun createGetStockInput() = GetStockInput(
    ticker = "BBAS3"
)

fun createGetStockOutput() = GetStockOutput(
    ticker = "BBAS3",
    price = Amount(27.55),
    name = "Banco do Brasil"
)

fun createStockProviderResponse() = StockProviderResponse(
    ticker = "BBAS3",
    price = Amount(27.55),
    name = "Banco do Brasil"
)

fun createSaveStockInput() = SaveStockInput(
    ticker = "BBAS3",
    price = Amount(27.55),
    name = "Banco do Brasil"
)
