package com.montebruni.holder.wallet.application.event.events

import com.montebruni.holder.fixtures.createWallet
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.Instant

class WalletCreatedEventTest {

    @Test
    fun `should get data from wallet created event`() {
        val wallet = createWallet().copy(createdAt = Instant.now())

        val event = WalletCreatedEvent(wallet)
        val eventData = event.getData()

        assertEquals(wallet.id, eventData.id)
        assertEquals(wallet.managerId, eventData.managerId)
        assertEquals(wallet.balance, eventData.balance)
        assertNotNull(eventData.createdAt)
    }
}
