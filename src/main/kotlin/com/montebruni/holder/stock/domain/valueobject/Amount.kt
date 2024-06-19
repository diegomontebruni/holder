package com.montebruni.holder.stock.domain.valueobject

import java.math.BigDecimal

data class Amount(val value: BigDecimal) {

    constructor(value: String) : this(BigDecimal(value))
    constructor(value: Double) : this(BigDecimal.valueOf(value))
}
