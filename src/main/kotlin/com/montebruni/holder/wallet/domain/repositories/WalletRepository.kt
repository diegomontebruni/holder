package com.montebruni.holder.wallet.domain.repositories

import com.montebruni.holder.wallet.domain.entity.Wallet
import java.util.UUID

interface WalletRepository {

    fun create(wallet: Wallet): Wallet
    fun findById(id: UUID): Wallet?
    fun findByManagerId(managerId: UUID): List<Wallet>
}
