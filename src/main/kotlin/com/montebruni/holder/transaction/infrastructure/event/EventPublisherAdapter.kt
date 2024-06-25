package com.montebruni.holder.transaction.infrastructure.event

import com.montebruni.holder.infrastructure.event.SpringEventPublisher
import com.montebruni.holder.transaction.application.event.EventPublisher
import com.montebruni.holder.transaction.application.event.events.Event
import org.springframework.stereotype.Component

@Component("TransactionEventPublisher")
class EventPublisherAdapter(
    private val publisher: SpringEventPublisher
) : EventPublisher {

    override fun publishEvent(event: Event) {
        publisher.publishEvent(event)
    }
}
