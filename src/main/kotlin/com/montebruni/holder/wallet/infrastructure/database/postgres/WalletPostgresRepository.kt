package com.montebruni.holder.wallet.infrastructure.database.postgres

import com.montebruni.holder.wallet.infrastructure.database.postgres.model.WalletPostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface WalletPostgresRepository : JpaRepository<WalletPostgresModel, UUID> {

    fun findByManagerId(managerId: UUID): List<WalletPostgresModel>
}
