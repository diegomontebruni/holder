package com.montebruni.holder.stock.application.usecase.output

import com.montebruni.holder.stock.domain.valueobject.Amount

data class GetStockOutput(
    val ticker: String,
    val price: Amount,
    val name: String
)
