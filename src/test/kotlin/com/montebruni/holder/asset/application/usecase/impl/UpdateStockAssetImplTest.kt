package com.montebruni.holder.asset.application.usecase.impl

import com.montebruni.holder.asset.application.event.EventPublisher
import com.montebruni.holder.asset.application.event.events.TransactionAssetFailedEvent
import com.montebruni.holder.asset.domain.entity.Asset
import com.montebruni.holder.asset.domain.repositories.AssetRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createAsset
import com.montebruni.holder.fixtures.createUpdateStockAssetInput
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.lang.Exception

class UpdateStockAssetImplTest(
    @MockK private val assetRepository: AssetRepository,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: UpdateStockAssetImpl

    @Test
    fun `should create asset successfully when asset not found`() {
        val input = createUpdateStockAssetInput()
        val repositorySlot = slot<Asset>()

        every { assetRepository.findByWalletIdAndTicker(input.walletId, input.ticker) } returns null
        every { assetRepository.save(capture(repositorySlot)) } answers { repositorySlot.captured }

        usecase.execute(input)

        assertEquals(input.walletId, repositorySlot.captured.walletId)
        assertEquals(input.ticker, repositorySlot.captured.ticker)
        assertEquals(input.quantity, repositorySlot.captured.quantity)
        assertEquals("10000.0", repositorySlot.captured.totalPaid.value.toString())
        assertEquals(input.value.value.toDouble(), repositorySlot.captured.averagePrice.value.toDouble())

        verify {
            assetRepository.findByWalletIdAndTicker(input.walletId, input.ticker)
            assetRepository.save(repositorySlot.captured)
        }
        verify(exactly = 0) {
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should update asset successfully when asset found`() {
        val input = createUpdateStockAssetInput()
        val asset = createAsset()
        val repositorySlot = slot<Asset>()

        every { assetRepository.findByWalletIdAndTicker(input.walletId, input.ticker) } returns asset
        every { assetRepository.save(capture(repositorySlot)) } answers { repositorySlot.captured }

        usecase.execute(input)

        assertEquals(asset.id, repositorySlot.captured.id)

        verify {
            assetRepository.findByWalletIdAndTicker(input.walletId, input.ticker)
            assetRepository.save(repositorySlot.captured)
        }
        verify(exactly = 0) {
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should publish event when asset update fails`() {
        val input = createUpdateStockAssetInput()
        val eventSlot = slot<TransactionAssetFailedEvent>()

        every { assetRepository.findByWalletIdAndTicker(input.walletId, input.ticker) } throws Exception()
        every { eventPublisher.publishEvent(capture(eventSlot)) } answers { eventSlot.captured }

        assertDoesNotThrow { usecase.execute(input) }

        assertEquals(input.transactionId, eventSlot.captured.data.transactionId)

        verify {
            assetRepository.findByWalletIdAndTicker(input.walletId, input.ticker)
            eventPublisher.publishEvent(eventSlot.captured)
        }
        verify(exactly = 0) {
            assetRepository.save(any())
        }
    }
}
