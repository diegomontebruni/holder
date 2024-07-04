package com.montebruni.holder.fixtures

import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.response.FindByTickerResponse

fun createFindByTickerResponse() = FindByTickerResponse(
    ticker = "AAPL",
    name = "Apple Inc.",
    price = 1.0,
)
