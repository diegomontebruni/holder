package com.montebruni.holder.wallet.infrastructure.event.listener

import com.montebruni.holder.account.application.event.events.CustomerCreatedEvent
import com.montebruni.holder.wallet.application.usecase.CreateWallet
import com.montebruni.holder.wallet.application.usecase.input.CreateWalletInput
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import kotlin.let

@Component
class CreateWalletListener(
    private val createWallet: CreateWallet
) {

    @EventListener(classes = [CustomerCreatedEvent::class])
    fun listener(event: CustomerCreatedEvent) {
        CreateWalletInput(
            customerId = event.getData().id ?: throw IllegalArgumentException("customer id is null"),
            managerId = event.getData().managerId
        ).let(createWallet::execute)
    }
}
