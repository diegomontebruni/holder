package com.montebruni.holder.stock.domain.entity

import com.montebruni.holder.stock.domain.valueobject.Amount

data class Stock(
    val ticker: String,
    val price: Amount,
    val name: String,
)
