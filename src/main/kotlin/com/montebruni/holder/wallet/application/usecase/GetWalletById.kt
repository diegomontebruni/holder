package com.montebruni.holder.wallet.application.usecase

import com.montebruni.holder.wallet.application.usecase.output.GetWalletByIdOutput
import java.util.UUID

interface GetWalletById {

    fun execute(walletId: UUID): GetWalletByIdOutput?
}
