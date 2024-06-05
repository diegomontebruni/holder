package com.montebruni.holder.wallet.entrypoint.eventlistener

import com.montebruni.holder.account.application.domain.events.CustomerCreatedEvent
import com.montebruni.holder.wallet.application.usecase.CreateWallet
import com.montebruni.holder.wallet.application.usecase.input.CreateWalletInput
import com.montebruni.holder.wallet.application.usecase.input.fromEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CreateWalletListener(
    private val createWallet: CreateWallet
) {

    @EventListener(classes = [CustomerCreatedEvent::class])
    fun listener(event: CustomerCreatedEvent) {
        CreateWalletInput.fromEvent(event).let(createWallet::execute)
    }
}
