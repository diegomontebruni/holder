package com.montebruni.holder.asset.infrastructure.event.listener

import com.montebruni.holder.asset.application.usecase.UpdateStockAsset
import com.montebruni.holder.asset.application.usecase.request.UpdateStockAssetInput
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createTransaction
import com.montebruni.holder.transaction.application.event.events.TransactionCreatedEvent
import com.montebruni.holder.transaction.domain.entity.Type
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UpdateStockAssetListenerTest(
    @MockK private val updateStockAsset: UpdateStockAsset
) : UnitTests() {

    @InjectMockKs
    private lateinit var listener: UpdateStockAssetListener

    @Test
    fun `should not call use case when event is not in accepted types`() {
        val event = createTransaction().copy(type = Type.CASH).let(::TransactionCreatedEvent)

        listener.handle(event)

        verify(exactly = 0) { updateStockAsset.execute(any()) }
    }

    @Test
    fun `should call use case when event is in accepted types`() {
        val event = createTransaction().let(::TransactionCreatedEvent)
        val useCaseSlot = slot<UpdateStockAssetInput>()

        justRun { updateStockAsset.execute(capture(useCaseSlot)) }

        listener.handle(event)

        val eventData = event.getData()
        val input = useCaseSlot.captured
        assertEquals(eventData.id, input.transactionId)
        assertEquals(eventData.status!!.name, input.transactionStatus.name)
        assertEquals(eventData.walletId, input.walletId)
        assertEquals(eventData.ticker, input.ticker)
        assertEquals(eventData.quantity, input.quantity)
        assertEquals(eventData.value!!.value, input.value.value)
        assertEquals(eventData.operation!!.name, input.operation.name)

        verify(exactly = 1) { updateStockAsset.execute(useCaseSlot.captured) }
    }
}
