package com.montebruni.holder.asset.infrastructure.database.postgres.model

import com.montebruni.holder.asset.domain.entity.Asset
import com.montebruni.holder.asset.domain.valueobject.Amount
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

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "asset")
data class AssetPostgresModel(

    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "wallet_id", updatable = false)
    val walletId: UUID,

    @Column(name = "ticker", updatable = false)
    val ticker: String,

    @Column(name = "quantity")
    val quantity: Int,

    @Column(name = "average_price")
    val averagePrice: Double,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now()
) {
    companion object
}

fun AssetPostgresModel.Companion.fromAsset(asset: Asset): AssetPostgresModel =
    AssetPostgresModel(
        walletId = asset.walletId,
        ticker = asset.ticker,
        quantity = asset.quantity,
        averagePrice = asset.averagePrice.value.toDouble()
    )

fun AssetPostgresModel.toAsset(): Asset =
    Asset(
        walletId = walletId,
        ticker = ticker,
        quantity = quantity,
        averagePrice = Amount(averagePrice),
    )