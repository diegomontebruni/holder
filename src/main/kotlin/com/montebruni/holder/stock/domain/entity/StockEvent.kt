package com.montebruni.holder.stock.domain.entity

import com.montebruni.holder.stock.domain.valueobject.Amount
import java.time.Instant

data class StockEvent(
    val ticker: String,
    val type: StockEventType,
    val amount: Amount,
    val description: String,
    val paymentDate: Instant,
    val approvedAt: Instant,
) {

    enum class StockEventType { DIVIDENDO, JCP, }
}
