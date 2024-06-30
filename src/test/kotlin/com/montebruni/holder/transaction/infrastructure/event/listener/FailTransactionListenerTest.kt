package com.montebruni.holder.transaction.infrastructure.event.listener

import com.montebruni.holder.asset.application.event.events.TransactionAssetFailedEvent
import com.montebruni.holder.asset.domain.event.TransactionAssetFailedEventData
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.transaction.application.usecase.FailTransaction
import com.montebruni.holder.transaction.application.usecase.input.FailTransactionInput
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class FailTransactionListenerTest(
    @MockK private val failTransaction: FailTransaction
) : UnitTests() {

    @InjectMockKs
    private lateinit var listener: FailTransactionListener

    @Test
    fun `should call use case successfully`() {
        val event = TransactionAssetFailedEventData(UUID.randomUUID()).let(::TransactionAssetFailedEvent)
        val useCaseInputSlot = slot<FailTransactionInput>()

        justRun { failTransaction.execute(capture(useCaseInputSlot)) }

        listener.handle(event)

        assertEquals(event.data.transactionId, useCaseInputSlot.captured.transactionId)

        verify(exactly = 1) { failTransaction.execute(useCaseInputSlot.captured) }
    }
}
