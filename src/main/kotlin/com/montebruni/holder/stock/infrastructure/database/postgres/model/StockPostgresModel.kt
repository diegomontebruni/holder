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

@Entity
@Table(name = "stock")
data class StockPostgresModel(

    @Id
    @Column(name = "ticker", nullable = false, unique = true)
    val ticker: String,

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
    ticker = stock.ticker,
    price = stock.price.value.toDouble(),
)

fun StockPostgresModel.toStock() = Stock(
    ticker = ticker,
    price = Amount(price),
)
