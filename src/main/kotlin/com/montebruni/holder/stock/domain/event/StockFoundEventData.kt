package com.montebruni.holder.stock.domain.event

import com.montebruni.holder.stock.domain.valueobject.Amount

data class StockFoundEventData(
    val ticker: String,
    val price: Amount,
    val name: String
) : EventData
