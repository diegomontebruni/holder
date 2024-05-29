package com.montebruni.holder.common.event.springevent

import com.montebruni.holder.common.event.Event
import com.montebruni.holder.common.event.EventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) : EventPublisher {

    override fun publishEvent(event: Event) {
        applicationEventPublisher.publishEvent(event)
    }
}
