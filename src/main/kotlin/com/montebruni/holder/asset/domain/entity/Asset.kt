package com.montebruni.holder.asset.domain.entity

import com.montebruni.holder.asset.domain.exception.InvalidOperationException
import com.montebruni.holder.asset.domain.valueobject.Amount
import java.math.BigDecimal
import java.util.UUID

data class Asset(
    val walletId: UUID,
    val ticker: String,
    val quantity: Int,
    val totalPaid: Amount,
    var averagePrice: Amount = Amount(BigDecimal.ZERO),
) {

    init {
        averagePrice = totalPaid.calculateAveragePrice(quantity)
    }

    fun update(quantity: Int, totalPaid: Amount, operation: Operation) =
        when (operation) {
            Operation.CREDIT -> copy(quantity = this.quantity + quantity, totalPaid = this.totalPaid.plus(totalPaid))
            Operation.DEBIT -> calculateDebit(quantity)
            else -> throw InvalidOperationException()
        }

    private fun calculateDebit(quantity: Int): Asset = copy(
        quantity = maxOf(0, this.quantity - quantity),
        totalPaid = Amount(
            maxOf(
                BigDecimal.ZERO,
                this.totalPaid.minus(averagePrice.multiply(Amount(quantity))).value
            )
        ),
    )
}
