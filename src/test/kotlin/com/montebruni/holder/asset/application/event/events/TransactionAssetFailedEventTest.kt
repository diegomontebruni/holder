package com.montebruni.holder.asset.application.event.events

import com.montebruni.holder.asset.domain.event.TransactionAssetFailedEventData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class TransactionAssetFailedEventTest {

    @Test
    fun `should create a TransactionAssetFailedEvent`() {
        val transactionId = UUID.randomUUID()
        val eventData = TransactionAssetFailedEventData(transactionId)

        val event = TransactionAssetFailedEvent(eventData)

        assertEquals(eventData, event.data)
    }
}
