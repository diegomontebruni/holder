package com.montebruni.holder.wallet.application.usecase

import com.montebruni.holder.wallet.application.usecase.input.CreateWalletInput
import com.montebruni.holder.wallet.application.usecase.output.CreateWalletOutput

interface CreateWallet {

    fun execute(input: CreateWalletInput): CreateWalletOutput
}
