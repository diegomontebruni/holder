package com.montebruni.holder.account.events.user

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserEventHandler {

    @Suppress("UnusedParameter", "ForbiddenComment")
    @EventListener(classes = [UserCreatedEvent::class])
    fun handle(event: UserCreatedEvent) {
        // TODO: Implement the logic to handle the event
    }
}
