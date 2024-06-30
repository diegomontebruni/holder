package com.montebruni.holder.asset.domain.entity

import com.montebruni.holder.asset.domain.exception.InvalidOperationException
import com.montebruni.holder.asset.domain.valueobject.Amount
import java.math.BigDecimal
import java.util.UUID

data class Asset(
    val id: UUID = UUID.randomUUID(),
    val walletId: UUID,
    val ticker: String,
    val quantity: Int,
    val totalPaid: Amount,
    var averagePrice: Amount = Amount(BigDecimal.ZERO),
) {

    constructor(walletId: UUID, ticker: String, quantity: Int, unitPrice: Amount) : this(
        walletId = walletId,
        ticker = ticker,
        quantity = quantity,
        totalPaid = unitPrice.multiply(Amount(quantity)),
        averagePrice = unitPrice
    )

    init {
        averagePrice = totalPaid.calculateAveragePrice(quantity)
    }

    fun update(quantity: Int, unitValue: Amount, operation: Operation) =
        when (operation) {
            Operation.CREDIT -> calculateCredit(quantity, unitValue)
            Operation.DEBIT -> calculateDebit(quantity)
            else -> throw InvalidOperationException()
        }

    private fun calculateCredit(quantity: Int, unitValue: Amount): Asset = copy(
        quantity = this.quantity + quantity,
        totalPaid = this.totalPaid.plus(unitValue.multiply(Amount(quantity)))
    )

    private fun calculateDebit(quantity: Int): Asset = copy(
        quantity = maxOf(0, this.quantity - quantity),
        totalPaid = quantity
            .takeIf { it == this.quantity }
            ?.let { Amount(BigDecimal.ZERO) }
            ?: Amount(
                maxOf(
                    BigDecimal.ZERO,
                    this.totalPaid.minus(averagePrice.multiply(Amount(quantity))).value
                )
            )
    )
}
