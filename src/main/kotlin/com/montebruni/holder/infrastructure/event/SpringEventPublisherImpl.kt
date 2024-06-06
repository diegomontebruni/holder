package com.montebruni.holder.infrastructure.event

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringEventPublisherImpl(
    private val applicationEventPublisher: ApplicationEventPublisher
) : SpringEventPublisher {

    override fun publishEvent(event: Any) {
        applicationEventPublisher.publishEvent(event)
    }
}
