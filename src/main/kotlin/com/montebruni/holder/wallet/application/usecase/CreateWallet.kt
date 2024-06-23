package com.montebruni.holder.wallet.application.usecase

import com.montebruni.holder.wallet.application.usecase.input.CreateWalletInput

interface CreateWallet {

    fun execute(input: CreateWalletInput)
}
