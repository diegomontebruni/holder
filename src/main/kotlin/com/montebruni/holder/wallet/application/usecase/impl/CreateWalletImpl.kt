package com.montebruni.holder.wallet.application.usecase.impl

import com.montebruni.holder.wallet.application.dataprovider.CustomerDataProvider
import com.montebruni.holder.wallet.application.dataprovider.exception.CustomerDataProviderNotFoundException
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
    private val customerDataProvider: CustomerDataProvider,
    private val eventPublisher: EventPublisher
) : CreateWallet {

    override fun execute(input: CreateWalletInput): CreateWalletOutput {
        customerDataProvider.findById(input.customerId) ?: throw CustomerDataProviderNotFoundException()
        input.managerId?.let {
            customerDataProvider.findById(input.managerId) ?: throw CustomerDataProviderNotFoundException()
        }

        return input
            .toWallet()
            .let(walletRepository::create)
            .also {
                WalletCreatedEvent(it).let(eventPublisher::publishEvent)
            }
            .let { CreateWalletOutput.fromWallet(it) }
    }
}
