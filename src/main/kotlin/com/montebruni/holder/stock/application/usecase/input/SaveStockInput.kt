package com.montebruni.holder.stock.application.usecase.input

import com.montebruni.holder.stock.domain.valueobject.Amount

data class SaveStockInput(
    val ticker: String,
    val price: Amount,
    val name: String
)
