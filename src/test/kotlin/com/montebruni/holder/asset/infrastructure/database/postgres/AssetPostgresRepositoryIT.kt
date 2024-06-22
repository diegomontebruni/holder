package com.montebruni.holder.asset.infrastructure.database.postgres

import com.montebruni.holder.configuration.DatabaseIT
import com.montebruni.holder.fixtures.createAssetPostgresModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID

class AssetPostgresRepositoryIT(
    @Autowired private val repository: AssetPostgresRepository
) : DatabaseIT(repository) {

    @Test
    fun `should save asset successfully`() {
        val assetModel = createAssetPostgresModel().also(repository::save)
        val savedAsset = repository.findByIdOrNull(assetModel.id)

        assertNotNull(savedAsset)
        assertEquals(assetModel, savedAsset)
    }

    @Test
    fun `should throw error when try to save asset with same wallet id and ticket`() {
        assertThrows<Exception> {
            createAssetPostgresModel()
                .also(repository::saveAndFlush)
                .let { it.copy(id = UUID.randomUUID(), quantity = 5) }
                .also(repository::saveAndFlush)
        }.run {
            assertTrue(message!!.contains("duplicate key value violates unique constraint \"unique_wallet_id_ticker\""))
        }
    }

    @Test
    fun `should update asset successfully with diff quantity and averagePrice`() {
        val quantity = 5
        val averagePrice = 50.0

        val assetModel = createAssetPostgresModel().also(repository::saveAndFlush)

        val updatedAsset = assetModel.copy(
            quantity = quantity,
            averagePrice = averagePrice
        ).let(repository::saveAndFlush)

        assertEquals(quantity, updatedAsset.quantity)
        assertEquals(averagePrice, updatedAsset.averagePrice)
        assertNotEquals(assetModel.updatedAt, updatedAsset.updatedAt)
    }
}
