package com.montebruni.holder.wallet.application.event

import com.montebruni.holder.wallet.application.event.events.Event

interface EventPublisher {

    fun publish(event: Event)
}
