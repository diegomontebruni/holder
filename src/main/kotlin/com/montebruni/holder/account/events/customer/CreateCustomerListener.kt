package com.montebruni.holder.account.events.customer

import com.montebruni.holder.account.events.user.UserCreatedEvent
import com.montebruni.holder.account.usecase.CreateCustomer
import com.montebruni.holder.account.usecase.input.CreateCustomerInput
import com.montebruni.holder.account.usecase.input.fromEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CreateCustomerListener(
    private val createCustomer: CreateCustomer
) {

    @EventListener(classes = [UserCreatedEvent::class])
    fun listener(event: UserCreatedEvent) {
        CreateCustomerInput.fromEvent(event).let(createCustomer::execute)
    }
}
