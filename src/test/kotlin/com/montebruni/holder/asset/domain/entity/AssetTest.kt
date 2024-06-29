package com.montebruni.holder.asset.domain.entity

import com.montebruni.holder.asset.domain.valueobject.Amount
import com.montebruni.holder.fixtures.createAsset
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AssetTest {

    @Test
    fun `should calculate average price`() {
        assertEquals(Amount(100).value.toDouble(), createAsset().averagePrice.value.toDouble())
    }

    @Test
    fun `should update asset when operation is credit and calculate average price`() {
        val multiplier = 2
        val asset = createAsset()

        val updatedAsset = asset.update(asset.quantity, asset.totalPaid, Operation.CREDIT)

        assertEquals(asset.quantity * multiplier, updatedAsset.quantity)
        assertEquals(asset.totalPaid.multiply(Amount(multiplier)), updatedAsset.totalPaid)
        assertEquals(asset.averagePrice.value.toDouble(), updatedAsset.averagePrice.value.toDouble())
    }

    @Test
    fun `should update asset when operation is debit and calculate average price`() {
        val asset = createAsset()
        val quantity = 5
        val totalPaid = Amount(100.0)
        val operation = Operation.DEBIT

        val updatedAsset = asset.update(quantity, totalPaid, operation)

        assertEquals(asset.quantity - quantity, updatedAsset.quantity)
        assertEquals(500.0, updatedAsset.totalPaid.value.toDouble())
        assertEquals(100.0, updatedAsset.averagePrice.value.toDouble())
    }

    @ParameterizedTest
    @ValueSource(ints = [10, 50])
    fun `should set to zero when quantity is zero or negative`(value: Int) {
        val asset = createAsset()
        val quantity = value
        val totalPaid = Amount(100.0)
        val operation = Operation.DEBIT

        val updatedAsset = asset.update(quantity, totalPaid, operation)

        assertEquals(0, updatedAsset.quantity)
        assertEquals(0.0, updatedAsset.totalPaid.value.toDouble())
        assertEquals(0.0, updatedAsset.averagePrice.value.toDouble())
    }
}
