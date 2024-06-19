package com.montebruni.holder.stock.infrastructure.database.postgres.model

import com.montebruni.holder.stock.domain.entity.Stock
import com.montebruni.holder.stock.domain.valueobject.Amount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "stock")
class StockPostgresModel(

    @Id
    @Column(updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "symbol", unique = true, nullable = false)
    val symbol: String,

    @Column(name = "price", nullable = false)
    val price: Double,

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now()
) {

    companion object
}

fun StockPostgresModel.Companion.fromStock(stock: Stock) = StockPostgresModel(
    symbol = stock.symbol,
    price = stock.price.value.toDouble(),
)

fun StockPostgresModel.toStock() = Stock(
    symbol = symbol,
    price = Amount(price),
)
