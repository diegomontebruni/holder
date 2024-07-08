package com.montebruni.holder.stock.presentation.interfaces.response

data class GetStockByTickerResponse(
    val ticker: String,
    val name: String,
    val price: Double
)
