package com.montebruni.holder.asset.domain.valueobject

import java.math.BigDecimal
import java.math.RoundingMode

data class Amount(val value: BigDecimal) {

    constructor(value: String) : this(BigDecimal(value))
    constructor(value: Double) : this(BigDecimal.valueOf(value))
    constructor(value: Int) : this(BigDecimal(value))

    fun calculateAveragePrice(quantity: Int) = quantity
        .takeIf { it > 0 }
        ?.let { Amount(value.divide(BigDecimal(quantity), 2, RoundingMode.HALF_UP)) }
        ?: Amount(BigDecimal.ZERO)

    fun multiply(amount: Amount) = Amount(value.multiply(amount.value))
    fun plus(amount: Amount) = Amount(value.plus(amount.value))
    fun minus(amount: Amount) = Amount(value.minus(amount.value))
}
