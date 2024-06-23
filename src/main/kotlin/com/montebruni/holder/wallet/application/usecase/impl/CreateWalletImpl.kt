package com.montebruni.holder.wallet.application.usecase.impl

import com.montebruni.holder.wallet.application.client.CustomerClient
import com.montebruni.holder.wallet.application.client.exception.CustomerNotFoundException
import com.montebruni.holder.wallet.application.event.EventPublisher
import com.montebruni.holder.wallet.application.event.events.WalletCreatedEvent
import com.montebruni.holder.wallet.application.usecase.CreateWallet
import com.montebruni.holder.wallet.application.usecase.input.CreateWalletInput
import com.montebruni.holder.wallet.application.usecase.input.toWallet
import com.montebruni.holder.wallet.domain.repositories.WalletRepository
import org.springframework.stereotype.Service
import kotlin.let

@Service
class CreateWalletImpl(
    private val walletRepository: WalletRepository,
    private val customerClient: CustomerClient,
    private val eventPublisher: EventPublisher
) : CreateWallet {

    override fun execute(input: CreateWalletInput) {
        customerClient.findById(input.customerId) ?: throw CustomerNotFoundException()
        input.managerId?.let {
            customerClient.findById(input.managerId) ?: throw CustomerNotFoundException()
        }

        input
            .toWallet()
            .let(walletRepository::create)
            .let { WalletCreatedEvent(it) }
            .let(eventPublisher::publishEvent)
    }
}
