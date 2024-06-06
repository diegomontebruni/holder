package com.montebruni.holder.account.infrastructure.event

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.Event
import com.montebruni.holder.infrastructure.event.SpringEventPublisher
import org.springframework.stereotype.Component

@Component
class EventPublisherAdapter(
    private val eventPublisher: SpringEventPublisher
) : EventPublisher {

    override fun publishEvent(event: Event) {
        eventPublisher.publishEvent(event)
    }
}
