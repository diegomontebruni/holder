package com.montebruni.holder.transaction.infrastructure.event.listener

import com.montebruni.holder.asset.application.event.events.TransactionAssetCreatedEvent
import com.montebruni.holder.asset.domain.event.TransactionAssetCreatedEventData
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.transaction.application.usecase.ConfirmTransaction
import com.montebruni.holder.transaction.application.usecase.input.ConfirmTransactionInput
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class ConfirmTransactionListenerTest(
    @MockK private val confirmTransaction: ConfirmTransaction
) : UnitTests() {

    @InjectMockKs
    private lateinit var listener: ConfirmTransactionListener

    @Test
    fun `should call use case successfully`() {
        val event = TransactionAssetCreatedEventData(UUID.randomUUID()).let(::TransactionAssetCreatedEvent)
        val useCaseInputSlot = slot<ConfirmTransactionInput>()

        justRun { confirmTransaction.execute(capture(useCaseInputSlot)) }

        listener.handle(event)

        assertEquals(event.data.transactionId, useCaseInputSlot.captured.transactionId)

        verify(exactly = 1) { confirmTransaction.execute(useCaseInputSlot.captured) }
    }
}
