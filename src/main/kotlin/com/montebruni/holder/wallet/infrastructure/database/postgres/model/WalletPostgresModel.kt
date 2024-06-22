package com.montebruni.holder.wallet.infrastructure.database.postgres.model

import com.montebruni.holder.wallet.domain.entity.Wallet
import com.montebruni.holder.wallet.domain.valueobject.Amount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.UUID
import kotlin.Double

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "wallet")
data class WalletPostgresModel(

    @Id
    @Column(updatable = false, unique = true,)
    val id: UUID,

    @Column(name = "manager_id")
    val managerId: UUID,

    @Column(name = "balance")
    val balance: Double,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now()
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
