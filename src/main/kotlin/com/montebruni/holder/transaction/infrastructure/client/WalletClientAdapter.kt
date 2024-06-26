package com.montebruni.holder.transaction.infrastructure.client

import com.montebruni.holder.transaction.application.client.WalletClient
import com.montebruni.holder.wallet.domain.repositories.WalletRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WalletClientAdapter(
    private val walletRepository: WalletRepository
) : WalletClient {

    override fun existsByWalletId(walletId: UUID): Boolean = walletRepository.findById(walletId)?.let { true } == true
}
