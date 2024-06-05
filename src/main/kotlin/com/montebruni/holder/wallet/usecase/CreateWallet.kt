package com.montebruni.holder.wallet.usecase

import com.montebruni.holder.account.domain.exception.CustomerNotFoundException
import com.montebruni.holder.account.domain.port.CustomerRepository
import com.montebruni.holder.common.event.EventPublisher
import com.montebruni.holder.wallet.domain.events.WalletCreatedEvent
import com.montebruni.holder.wallet.domain.port.WalletRepository
import com.montebruni.holder.wallet.usecase.input.CreateWalletInput
import com.montebruni.holder.wallet.usecase.input.toWallet
import com.montebruni.holder.wallet.usecase.output.CreateWalletOutput
import com.montebruni.holder.wallet.usecase.output.fromWallet
import org.springframework.stereotype.Service

@Service
class CreateWallet(
    private val walletRepository: WalletRepository,
    private val customerRepository: CustomerRepository,
    private val eventPublisher: EventPublisher
) {

    fun execute(input: CreateWalletInput): CreateWalletOutput {
        customerRepository.findById(input.customerId) ?: throw CustomerNotFoundException()
        input.managerId?.let { customerRepository.findById(input.managerId) ?: throw CustomerNotFoundException() }

        return input
            .toWallet()
            .let(walletRepository::create)
            .also {
                WalletCreatedEvent(it).let(eventPublisher::publishEvent)
            }
            .let { CreateWalletOutput.fromWallet(it) }
    }
}
