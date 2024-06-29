package com.montebruni.holder.asset.application.event

import com.montebruni.holder.asset.application.event.events.Event

interface EventPublisher {

    fun publishEvent(event: Event)
}
