package com.montebruni.holder.wallet.application.usecase.impl

import com.montebruni.holder.wallet.application.client.UserClient
import com.montebruni.holder.wallet.application.client.exception.UserNotFoundException
import com.montebruni.holder.wallet.application.usecase.CreateWallet
import com.montebruni.holder.wallet.application.usecase.input.CreateWalletInput
import com.montebruni.holder.wallet.application.usecase.input.toWallet
import com.montebruni.holder.wallet.domain.repositories.WalletRepository
import org.springframework.stereotype.Service
import kotlin.let

@Service
class CreateWalletImpl(
    private val walletRepository: WalletRepository,
    private val userClient: UserClient
) : CreateWallet {

    override fun execute(input: CreateWalletInput) {
        userClient.findById(input.userId) ?: throw UserNotFoundException()
        input.managerId?.let {
            userClient.findById(input.managerId) ?: throw UserNotFoundException()
        }

        input
            .toWallet()
            .let(walletRepository::create)
    }
}
