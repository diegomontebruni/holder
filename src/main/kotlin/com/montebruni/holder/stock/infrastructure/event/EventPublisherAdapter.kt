package com.montebruni.holder.stock.infrastructure.event

import com.montebruni.holder.infrastructure.event.SpringEventPublisher
import com.montebruni.holder.stock.application.event.EventPublisher
import com.montebruni.holder.stock.application.event.events.Event
import org.springframework.stereotype.Component

@Component("StockEventPublisher")
class EventPublisherAdapter(
    private val eventPublisher: SpringEventPublisher
) : EventPublisher {

    override fun publishEvent(event: Event) {
        eventPublisher.publishEvent(event)
    }
}
