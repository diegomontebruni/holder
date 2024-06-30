package com.montebruni.holder.asset.application.usecase.impl

import com.montebruni.holder.asset.application.client.TransactionClient
import com.montebruni.holder.asset.application.client.exception.TransactionNotFoundException
import com.montebruni.holder.asset.application.client.response.TransactionResponse.TransactionResponseStatus
import com.montebruni.holder.asset.application.event.EventPublisher
import com.montebruni.holder.asset.application.event.events.TransactionAssetCreatedEvent
import com.montebruni.holder.asset.application.event.events.TransactionAssetFailedEvent
import com.montebruni.holder.asset.domain.entity.Asset
import com.montebruni.holder.asset.domain.repositories.AssetRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createAsset
import com.montebruni.holder.fixtures.createTransactionResponse
import com.montebruni.holder.fixtures.createUpdateStockAssetInput
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class UpdateStockAssetImplTest(
    @MockK private val assetRepository: AssetRepository,
    @MockK private val transactionClient: TransactionClient,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: UpdateStockAssetImpl

    private val input = createUpdateStockAssetInput()
    private val transactionResponse = createTransactionResponse()

    @ParameterizedTest
    @EnumSource(value = TransactionResponseStatus::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
    fun `should do nothing when transaction status is not pending`(status: TransactionResponseStatus) {
        val response = transactionResponse.copy(status = status)

        every { transactionClient.findById(input.transactionId) } returns response

        assertDoesNotThrow { usecase.execute(input) }

        verify { transactionClient.findById(input.transactionId) }
        verify(exactly = 0) {
            assetRepository.findByWalletIdAndTicker(any(), any())
            assetRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should create asset successfully when asset not found`() {
        val repositorySlot = slot<Asset>()
        val eventSlot = slot<TransactionAssetCreatedEvent>()

        every { transactionClient.findById(input.transactionId) } returns transactionResponse
        every { assetRepository.findByWalletIdAndTicker(input.walletId, input.ticker) } returns null
        every { assetRepository.save(capture(repositorySlot)) } answers { repositorySlot.captured }
        every { eventPublisher.publishEvent(capture(eventSlot)) } answers { eventSlot.captured }

        usecase.execute(input)

        assertEquals(input.walletId, repositorySlot.captured.walletId)
        assertEquals(input.ticker, repositorySlot.captured.ticker)
        assertEquals(input.quantity, repositorySlot.captured.quantity)
        assertEquals("10000.0", repositorySlot.captured.totalPaid.value.toString())
        assertEquals(input.value.value.toDouble(), repositorySlot.captured.averagePrice.value.toDouble())

        assertEquals(input.transactionId, eventSlot.captured.data.transactionId)

        verify(exactly = 1) {
            assetRepository.findByWalletIdAndTicker(input.walletId, input.ticker)
            assetRepository.save(repositorySlot.captured)
            transactionClient.findById(input.transactionId)
            eventPublisher.publishEvent(eventSlot.captured)
        }
    }

    @Test
    fun `should update asset successfully when asset found`() {
        val asset = createAsset()
        val repositorySlot = slot<Asset>()
        val eventSlot = slot<TransactionAssetCreatedEvent>()

        every { transactionClient.findById(input.transactionId) } returns transactionResponse
        every { assetRepository.findByWalletIdAndTicker(input.walletId, input.ticker) } returns asset
        every { assetRepository.save(capture(repositorySlot)) } answers { repositorySlot.captured }
        every { eventPublisher.publishEvent(capture(eventSlot)) } answers { eventSlot.captured }

        usecase.execute(input)

        assertEquals(asset.id, repositorySlot.captured.id)
        assertEquals(input.transactionId, eventSlot.captured.data.transactionId)

        verify(exactly = 1) {
            assetRepository.findByWalletIdAndTicker(input.walletId, input.ticker)
            assetRepository.save(repositorySlot.captured)
            transactionClient.findById(input.transactionId)
            eventPublisher.publishEvent(eventSlot.captured)
        }
    }

    @Test
    fun `should publish event when asset update fails`() {
        val eventSlot = slot<TransactionAssetFailedEvent>()

        every { transactionClient.findById(input.transactionId) } throws TransactionNotFoundException()
        every { eventPublisher.publishEvent(capture(eventSlot)) } answers { eventSlot.captured }

        assertDoesNotThrow { usecase.execute(input) }

        assertEquals(input.transactionId, eventSlot.captured.data.transactionId)

        verify(exactly = 1) {
            eventPublisher.publishEvent(eventSlot.captured)
            transactionClient.findById(input.transactionId)
        }
        verify(exactly = 0) {
            assetRepository.findByWalletIdAndTicker(any(), any())
            assetRepository.save(any())
        }
    }
}
