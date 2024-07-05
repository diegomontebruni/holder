package com.montebruni.holder.stock.infrastructure.database.postgres.model

import com.montebruni.holder.stock.domain.entity.Stock
import com.montebruni.holder.stock.domain.valueobject.Amount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "stock")
data class StockPostgresModel(

    @Id
    @Column(name = "ticker", unique = true, updatable = false)
    val ticker: String,

    @Column(name = "price")
    val price: Double,

    @Column(name = "name")
    val name: String,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now()
) {

    companion object
}

fun StockPostgresModel.Companion.fromStock(stock: Stock) = StockPostgresModel(
    ticker = stock.ticker,
    price = stock.price.value.toDouble(),
    name = stock.name
)

fun StockPostgresModel.toStock() = Stock(
    ticker = ticker,
    price = Amount(price),
    name = name,
)
