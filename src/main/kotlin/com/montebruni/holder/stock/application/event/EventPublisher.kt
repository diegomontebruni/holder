package com.montebruni.holder.stock.application.event

import com.montebruni.holder.stock.application.event.events.Event

interface EventPublisher {

    fun publishEvent(event: Event)
}
