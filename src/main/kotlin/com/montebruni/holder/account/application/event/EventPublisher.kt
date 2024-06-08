package com.montebruni.holder.account.application.event

import com.montebruni.holder.account.application.event.events.Event

interface EventPublisher {

    fun publish(event: Event)
}
