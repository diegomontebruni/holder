package com.montebruni.holder.stock.application.provider

import com.montebruni.holder.stock.domain.valueobject.Amount

data class StockProviderResponse(
    val ticker: String,
    val name: String,
    val price: Amount
)
