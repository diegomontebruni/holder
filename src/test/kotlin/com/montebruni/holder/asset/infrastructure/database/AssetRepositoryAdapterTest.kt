package com.montebruni.holder.asset.infrastructure.database

import com.montebruni.holder.asset.infrastructure.database.postgres.AssetPostgresRepository
import com.montebruni.holder.asset.infrastructure.database.postgres.model.AssetPostgresModel
import com.montebruni.holder.asset.infrastructure.database.postgres.model.fromAsset
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createAsset
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.UUID.randomUUID

class AssetRepositoryAdapterTest(
    @MockK private val repository: AssetPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: AssetRepositoryAdapter

    @Nested
    inner class SaveCases {

        @Test
        fun `should save asset successfully`() {
            val asset = createAsset()
            val modelSlot = slot<AssetPostgresModel>()

            every { repository.save(capture(modelSlot)) } answers { modelSlot.captured }

            val result = adapter.save(asset)

            val modelCaptured = modelSlot.captured
            assertEquals(asset.walletId, modelCaptured.walletId)
            assertEquals(asset.ticker, modelCaptured.ticker)
            assertEquals(asset.quantity, modelCaptured.quantity)
            assertEquals(asset.totalPaid.value.toDouble(), modelCaptured.totalPaid)
            assertEquals(asset.averagePrice.value.toDouble(), modelCaptured.averagePrice)

            assertEquals(asset, result)

            verify(exactly = 1) { repository.save(modelSlot.captured) }
        }
    }

    @Nested
    inner class FindByWalletIdAndTickerCases {

        @Test
        fun `should find asset by wallet id and ticker successfully`() {
            val walletId = randomUUID()
            val ticker = "PETR4"
            val asset = createAsset().copy(walletId = walletId, ticker = ticker)
            val model = AssetPostgresModel.fromAsset(asset)

            every { repository.findByWalletIdAndTicker(walletId, ticker) } returns model

            val result = adapter.findByWalletIdAndTicker(walletId, ticker)

            assertEquals(asset, result)

            verify(exactly = 1) { repository.findByWalletIdAndTicker(walletId, ticker) }
        }
    }
}
