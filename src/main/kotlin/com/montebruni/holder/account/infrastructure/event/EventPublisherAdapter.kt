package com.montebruni.holder.account.infrastructure.event

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.Event
import com.montebruni.holder.infrastructure.event.SpringEventPublisher
import org.springframework.stereotype.Component

@Component("accountEventPublisher")
class EventPublisherAdapter(
    private val publisher: SpringEventPublisher
) : EventPublisher {

    override fun publish(event: Event) {
        publisher.publishEvent(event)
    }
}
