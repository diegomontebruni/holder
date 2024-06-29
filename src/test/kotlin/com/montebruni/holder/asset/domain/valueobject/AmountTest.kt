package com.montebruni.holder.asset.domain.valueobject

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class AmountTest {

    @Nested
    inner class ConstructorCases {

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

        @Test
        fun `should create amount from int`() {
            val int = 10

            assertDoesNotThrow { Amount(int) }.run {
                assertEquals(int.toString(), this.value.toString())
            }
        }
    }

    @Nested
    inner class CalculateAveragePriceCases {

        @Test
        fun `should calculate average price`() {
            val amount = Amount(10.00)
            val quantity = 2

            val result = amount.calculateAveragePrice(quantity)

            assertEquals("5.00", result.value.toString())
        }

        @Test
        fun `should return zero when quantity is zero`() {
            val amount = Amount(10.00)
            val quantity = 0

            val result = amount.calculateAveragePrice(quantity)

            assertEquals("0", result.value.toString())
        }
    }

    @Nested
    inner class MultiplyCases {

        @Test
        fun `should multiply amount`() {
            val amount = Amount(10.00)
            val other = Amount(2.00)

            val result = amount.multiply(other)

            assertEquals("20.00", result.value.toString())
        }
    }

    @Nested
    inner class PlusCases {

        @Test
        fun `should sum amount`() {
            val amount = Amount(10.00)
            val other = Amount(2.00)

            val result = amount.plus(other)

            assertEquals("12.0", result.value.toString())
        }
    }

    @Nested
    inner class MinusCases {

        @Test
        fun `should subtract amount`() {
            val amount = Amount(10.00)
            val other = Amount(2.00)

            val result = amount.minus(other)

            assertEquals("8.0", result.value.toString())
        }
    }
}
