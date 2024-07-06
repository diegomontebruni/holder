package com.montebruni.holder.stock.infrastructure.event

import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.infrastructure.event.SpringEventPublisher
import com.montebruni.holder.stock.application.event.events.Event
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EventPublisherAdapterTest(
    @MockK private val eventPublisher: SpringEventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var eventPublisherAdapter: EventPublisherAdapter

    @Test
    fun `should publish event successfully`() {
        val event = mockk<Event>()
        val eventSlot = slot<Event>()

        justRun { eventPublisher.publishEvent(capture(eventSlot)) }

        eventPublisherAdapter.publishEvent(event)

        assertThat(eventSlot.captured).isEqualTo(event)

        verify { eventPublisher.publishEvent(eventSlot.captured) }
    }
}
