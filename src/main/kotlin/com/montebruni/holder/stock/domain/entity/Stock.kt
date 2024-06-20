package com.montebruni.holder.stock.domain.entity

import com.montebruni.holder.stock.domain.valueobject.Amount

data class Stock(
    val symbol: String,
    val price: Amount,
)
