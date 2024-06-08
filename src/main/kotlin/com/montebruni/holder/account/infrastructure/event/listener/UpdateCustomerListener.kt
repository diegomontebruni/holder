package com.montebruni.holder.account.infrastructure.event.listener

import com.montebruni.holder.account.application.event.events.CustomerRegistrationCompletedEvent
import com.montebruni.holder.account.application.usecase.UpdateCustomer
import com.montebruni.holder.account.application.usecase.input.UpdateCustomerInput
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UpdateCustomerListener(
    private val updateCustomer: UpdateCustomer
) {

    @EventListener(classes = [CustomerRegistrationCompletedEvent::class])
    fun listener(event: CustomerRegistrationCompletedEvent) {
        UpdateCustomerInput(
            id = event.getData().id!!,
            name = event.getData().name!!
        ).let(updateCustomer::execute)
    }
}
