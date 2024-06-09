package com.montebruni.holder.account.infrastructure.event.listener

import com.montebruni.holder.account.application.event.events.CustomerRegistrationCompletedEvent
import com.montebruni.holder.account.application.usecase.CompleteUserRegistration
import com.montebruni.holder.account.application.usecase.input.CompleteUserRegistrationInput
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CompleteUserRegistrationListener(
    private val completeUserRegistration: CompleteUserRegistration
) {

    @EventListener(classes = [CustomerRegistrationCompletedEvent::class])
    fun listener(event: CustomerRegistrationCompletedEvent) {
        CompleteUserRegistrationInput(
            userId = event.getData().userId!!
        ).let(completeUserRegistration::execute)
    }
}
