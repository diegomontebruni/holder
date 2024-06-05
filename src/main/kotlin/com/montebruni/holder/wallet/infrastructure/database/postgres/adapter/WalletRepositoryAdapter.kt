package com.montebruni.holder.wallet.infrastructure.database.postgres.adapter

import com.montebruni.holder.wallet.application.domain.entity.Wallet
import com.montebruni.holder.wallet.application.domain.port.WalletRepository
import com.montebruni.holder.wallet.infrastructure.database.postgres.model.WalletPostgresModel
import com.montebruni.holder.wallet.infrastructure.database.postgres.model.fromWallet
import com.montebruni.holder.wallet.infrastructure.database.postgres.model.toWallet
import com.montebruni.holder.wallet.infrastructure.database.postgres.repository.WalletPostgresRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WalletRepositoryAdapter(
    private val repository: WalletPostgresRepository
) : WalletRepository {

    override fun create(wallet: Wallet): Wallet =
        WalletPostgresModel
            .fromWallet(wallet)
            .let(repository::save)
            .toWallet()

    override fun findById(id: UUID): Wallet? = repository.findByIdOrNull(id)?.let { it.toWallet() }

    override fun findByManagerId(managerId: UUID): List<Wallet> =
        repository.findByManagerId(managerId).map { it.toWallet() }
}
