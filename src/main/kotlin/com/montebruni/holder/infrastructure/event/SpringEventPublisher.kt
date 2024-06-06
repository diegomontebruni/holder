package com.montebruni.holder.infrastructure.event

interface SpringEventPublisher {

    fun publishEvent(event: Any)
}
