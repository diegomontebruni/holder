package com.montebruni.holder.wallet.infrastructure.database.postgres.model

import com.montebruni.holder.wallet.application.domain.entity.Wallet
import com.montebruni.holder.wallet.application.domain.valueobject.Amount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import java.util.UUID
import kotlin.Double

@Entity
@Table(name = "wallet")
class WalletPostgresModel(

    @Id
    @Column(updatable = false)
    val id: UUID,

    @Column(name = "manager_id", nullable = false)
    val managerId: UUID,

    @Column(name = "balance", nullable = false)
    val balance: Double,

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),
) {

    companion object
}

fun WalletPostgresModel.Companion.fromWallet(wallet: Wallet) = WalletPostgresModel(
    id = wallet.id,
    managerId = wallet.managerId,
    balance = wallet.balance.value.toDouble()
)

fun WalletPostgresModel.toWallet() = Wallet(
    id = id,
    managerId = managerId,
    balance = Amount(balance),
    createdAt = createdAt
)
