package com.montebruni.holder.wallet.infrastructure.event

import com.montebruni.holder.infrastructure.event.SpringEventPublisher
import com.montebruni.holder.wallet.application.event.EventPublisher
import com.montebruni.holder.wallet.application.event.events.Event
import org.springframework.stereotype.Component

@Component
class EventPublisherAdapter(
    private val eventPublisher: SpringEventPublisher
) : EventPublisher {

    override fun publishEvent(event: Event) {
        eventPublisher.publishEvent(event)
    }
}
