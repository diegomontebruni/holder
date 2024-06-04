package com.montebruni.holder.account.domain.events.listener

import com.montebruni.holder.account.domain.events.data.UserCreatedEvent
import com.montebruni.holder.account.usecase.CreateCustomer
import com.montebruni.holder.account.usecase.input.CreateCustomerInput
import com.montebruni.holder.account.usecase.input.fromEvent
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
