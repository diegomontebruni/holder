package com.montebruni.holder.transaction.application.event

import com.montebruni.holder.transaction.application.event.events.Event

interface EventPublisher {

    fun publishEvent(event: Event)
}
