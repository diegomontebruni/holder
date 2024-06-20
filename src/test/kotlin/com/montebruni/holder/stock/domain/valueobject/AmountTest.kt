package com.montebruni.holder.stock.domain.valueobject

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class AmountTest {

    @Test
    fun `should create amount from string`() {
        val str = "10.00"

        assertDoesNotThrow { Amount(str) }.run {
            assertEquals(str, this.value.toString())
        }
    }

    @Test
    fun `should create amount from double`() {
        val double = 10.00

        assertDoesNotThrow { Amount(double) }.run {
            assertEquals(double.toString(), this.value.toString())
        }
    }
}
