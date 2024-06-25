package com.montebruni.holder.transaction.application.event.events

import com.montebruni.holder.fixtures.createTransaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant

class TransactionCreatedEventTest {

    @Test
    fun `should get data from transaction created event`() {
        val entity = createTransaction().copy(createdAt = Instant.now(), description = "description")

        val event = TransactionCreatedEvent(entity)
        val eventData = event.getData()

        assertEquals(entity.id, eventData.id)
        assertEquals(entity.status, eventData.status)
        assertEquals(entity.walletId, eventData.walletId)
        assertEquals(entity.ticker, eventData.ticker)
        assertEquals(entity.quantity, eventData.quantity)
        assertEquals(entity.value, eventData.value)
        assertEquals(entity.operation, eventData.operation)
        assertEquals(entity.type, eventData.type)
        assertEquals(entity.description, eventData.description)
        assertEquals(entity.createdAt, eventData.createdAt)
    }
}
