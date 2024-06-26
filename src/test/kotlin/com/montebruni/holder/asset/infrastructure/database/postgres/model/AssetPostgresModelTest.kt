package com.montebruni.holder.asset.infrastructure.database.postgres.model

import com.montebruni.holder.fixtures.createAsset
import com.montebruni.holder.fixtures.createAssetPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class AssetPostgresModelTest {

    @Test
    fun `should convert from asset to model`() {
        val asset = createAsset()
        val model = AssetPostgresModel.fromAsset(asset)

        assertEquals(asset.id, model.id)
        assertEquals(asset.walletId, model.walletId)
        assertEquals(asset.ticker, model.ticker)
        assertEquals(asset.quantity, model.quantity)
        assertEquals(asset.totalPaid.value.toDouble(), model.totalPaid)
        assertEquals(asset.averagePrice.value.toDouble(), model.averagePrice)
        assertNotNull(model.createdAt)
        assertNotNull(model.updatedAt)
    }

    @Test
    fun `should convert from model to asset`() {
        val model = createAssetPostgresModel()
        val asset = model.toAsset()

        assertEquals(model.id, asset.id)
        assertEquals(model.walletId, asset.walletId)
        assertEquals(model.ticker, asset.ticker)
        assertEquals(model.quantity, asset.quantity)
        assertEquals(model.totalPaid, asset.totalPaid.value.toDouble())
        assertEquals(model.averagePrice, asset.averagePrice.value.toDouble())
    }
}
