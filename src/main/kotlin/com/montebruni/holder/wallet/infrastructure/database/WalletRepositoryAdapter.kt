package com.montebruni.holder.wallet.infrastructure.database

import com.montebruni.holder.wallet.domain.entity.Wallet
import com.montebruni.holder.wallet.domain.repositories.WalletRepository
import com.montebruni.holder.wallet.infrastructure.database.postgres.WalletPostgresRepository
import com.montebruni.holder.wallet.infrastructure.database.postgres.model.WalletPostgresModel
import com.montebruni.holder.wallet.infrastructure.database.postgres.model.fromWallet
import com.montebruni.holder.wallet.infrastructure.database.postgres.model.toWallet
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.collections.map
import kotlin.let

@Component
class WalletRepositoryAdapter(
    private val repository: WalletPostgresRepository
) : WalletRepository {

    override fun create(wallet: Wallet): Wallet =
        WalletPostgresModel
            .fromWallet(wallet)
            .let(repository::save)
            .toWallet()

    override fun findById(id: UUID): Wallet? = repository.findByIdOrNull(id)?.toWallet()

    override fun findByManagerId(managerId: UUID): List<Wallet> =
        repository.findByManagerId(managerId).map { it.toWallet() }
}
