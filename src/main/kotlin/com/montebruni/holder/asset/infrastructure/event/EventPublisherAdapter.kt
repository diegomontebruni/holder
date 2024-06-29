package com.montebruni.holder.asset.infrastructure.event

import com.montebruni.holder.asset.application.event.EventPublisher
import com.montebruni.holder.asset.application.event.events.Event
import com.montebruni.holder.infrastructure.event.SpringEventPublisher
import org.springframework.stereotype.Component

@Component("AssetEventPublisher")
class EventPublisherAdapter(
    private val eventPublisher: SpringEventPublisher
) : EventPublisher {

    override fun publishEvent(event: Event) {
        eventPublisher.publishEvent(event)
    }
}
