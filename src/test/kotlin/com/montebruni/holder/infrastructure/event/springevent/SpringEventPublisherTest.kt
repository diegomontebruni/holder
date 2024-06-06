package com.montebruni.holder.infrastructure.event.springevent

import com.montebruni.holder.common.event.AccountEvent
import com.montebruni.holder.configuration.UnitTests
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher

class SpringEventPublisherTest(
    @MockK private val applicationEventPublisher: ApplicationEventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var springEventPublisher: SpringEventPublisher

    @Test
    fun `should publish event`() {
        val event = mockk<AccountEvent>()
        val eventSlot = slot<AccountEvent>()

        justRun { applicationEventPublisher.publishEvent(capture(eventSlot)) }

        springEventPublisher.publishEvent(event)

        assertThat(eventSlot.captured).isEqualTo(event)

        verify { applicationEventPublisher.publishEvent(eventSlot.captured) }
    }
}
