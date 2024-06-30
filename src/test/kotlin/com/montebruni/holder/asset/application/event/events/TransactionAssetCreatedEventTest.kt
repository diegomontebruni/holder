package com.montebruni.holder.asset.application.event.events

import com.montebruni.holder.asset.domain.event.TransactionAssetCreatedEventData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class TransactionAssetCreatedEventTest {

    @Test
    fun `should create a TransactionAssetCreatedEvent`() {
        val transactionId = UUID.randomUUID()
        val eventData = TransactionAssetCreatedEventData(transactionId)

        val event = TransactionAssetCreatedEvent(eventData)

        assertEquals(eventData, event.data)
    }
}
