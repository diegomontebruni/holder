package com.montebruni.holder.wallet.infrastructure.event

import com.montebruni.holder.infrastructure.event.SpringEventPublisher
import com.montebruni.holder.wallet.application.event.EventPublisher
import com.montebruni.holder.wallet.application.event.events.Event
import org.springframework.stereotype.Component

@Component("walletEventPublisher")
class EventPublisherAdapter(
    private val publisher: SpringEventPublisher
) : EventPublisher {

    override fun publish(event: Event) {
        publisher.publishEvent(event)
    }
}
