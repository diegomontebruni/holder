package com.montebruni.holder.wallet.application.usecase.impl

import com.montebruni.holder.wallet.application.client.CustomerClient
import com.montebruni.holder.wallet.application.client.exception.CustomerNotFoundException
import com.montebruni.holder.wallet.application.event.EventPublisher
import com.montebruni.holder.wallet.application.event.events.WalletCreatedEvent
import com.montebruni.holder.wallet.application.usecase.CreateWallet
import com.montebruni.holder.wallet.application.usecase.input.CreateWalletInput
import com.montebruni.holder.wallet.application.usecase.input.toWallet
import com.montebruni.holder.wallet.application.usecase.output.CreateWalletOutput
import com.montebruni.holder.wallet.application.usecase.output.fromWallet
import com.montebruni.holder.wallet.domain.repositories.WalletRepository
import org.springframework.stereotype.Service
import kotlin.also
import kotlin.let

@Service
class CreateWalletImpl(
    private val walletRepository: WalletRepository,
    private val customerClient: CustomerClient,
    private val eventPublisher: EventPublisher
) : CreateWallet {

    override fun execute(input: CreateWalletInput): CreateWalletOutput {
        customerClient.findById(input.customerId) ?: throw CustomerNotFoundException()
        input.managerId?.let {
            customerClient.findById(input.managerId) ?: throw CustomerNotFoundException()
        }

        return input
            .toWallet()
            .let(walletRepository::create)
            .also {
                WalletCreatedEvent(it).let(eventPublisher::publish)
            }
            .let { CreateWalletOutput.fromWallet(it) }
    }
}
