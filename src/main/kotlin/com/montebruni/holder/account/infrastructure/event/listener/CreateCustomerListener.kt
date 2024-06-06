package com.montebruni.holder.account.infrastructure.event.listener

import com.montebruni.holder.account.application.event.events.UserCreatedEvent
import com.montebruni.holder.account.application.usecase.CreateCustomer
import com.montebruni.holder.account.application.usecase.input.CreateCustomerInput
import com.montebruni.holder.account.application.usecase.input.fromEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import kotlin.let

@Component
class CreateCustomerListener(
    private val createCustomer: CreateCustomer
) {

    @EventListener(classes = [UserCreatedEvent::class])
    fun listener(event: UserCreatedEvent) {
        CreateCustomerInput.fromEvent(event).let(createCustomer::execute)
    }
}
