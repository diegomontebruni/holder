package com.montebruni.holder.wallet.application.usecase.impl

import com.montebruni.holder.account.application.domain.exception.CustomerNotFoundException
import com.montebruni.holder.account.application.domain.port.CustomerRepository
import com.montebruni.holder.common.event.EventPublisher
import com.montebruni.holder.wallet.application.domain.events.WalletCreatedEvent
import com.montebruni.holder.wallet.application.domain.port.WalletRepository
import com.montebruni.holder.wallet.application.usecase.CreateWallet
import com.montebruni.holder.wallet.application.usecase.input.CreateWalletInput
import com.montebruni.holder.wallet.application.usecase.input.toWallet
import com.montebruni.holder.wallet.application.usecase.output.CreateWalletOutput
import com.montebruni.holder.wallet.application.usecase.output.fromWallet
import org.springframework.stereotype.Service
import kotlin.also
import kotlin.let

@Service
class CreateWalletImpl(
    private val walletRepository: WalletRepository,
    private val customerRepository: CustomerRepository,
    private val eventPublisher: EventPublisher
) : CreateWallet {

    override fun execute(input: CreateWalletInput): CreateWalletOutput {
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
