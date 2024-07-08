package com.montebruni.holder.transaction.infrastructure.client

import com.montebruni.holder.transaction.application.client.WalletClient
import com.montebruni.holder.wallet.presentation.interfaces.WalletInterface
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WalletClientAdapter(
    private val walletInterface: WalletInterface
) : WalletClient {

    override fun existsByWalletId(walletId: UUID): Boolean =
        walletInterface.getWalletById(walletId)?.let { true } == true
}
