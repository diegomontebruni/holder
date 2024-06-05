package com.montebruni.holder.wallet.eventlistener

import com.montebruni.holder.account.domain.events.CustomerCreatedEvent
import com.montebruni.holder.wallet.usecase.CreateWallet
import com.montebruni.holder.wallet.usecase.input.CreateWalletInput
import com.montebruni.holder.wallet.usecase.input.fromEvent
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
