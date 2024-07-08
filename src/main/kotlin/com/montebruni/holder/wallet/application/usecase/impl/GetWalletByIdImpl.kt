package com.montebruni.holder.wallet.application.usecase.impl

import com.montebruni.holder.wallet.application.usecase.GetWalletById
import com.montebruni.holder.wallet.application.usecase.output.GetWalletByIdOutput
import com.montebruni.holder.wallet.domain.repositories.WalletRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetWalletByIdImpl(
    private val walletRepository: WalletRepository
) : GetWalletById {

    override fun execute(walletId: UUID): GetWalletByIdOutput? =
        walletRepository.findById(walletId)?.let {
            GetWalletByIdOutput(
                id = it.id,
                managerId = it.managerId,
                balance = it.balance,
                createdAt = it.createdAt ?: throw IllegalArgumentException("Wallet created at is required")
            )
        }
}
