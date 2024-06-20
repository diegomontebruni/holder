package com.montebruni.holder.stock.infrastructure.database.postgres.model

import com.montebruni.holder.stock.domain.entity.StockEvent
import com.montebruni.holder.stock.domain.valueobject.Amount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "stock_event")
data class StockEventPostgresModel(

    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "symbol", updatable = false)
    val symbol: String,

    @Column(name = "type", updatable = false)
    val type: String,

    @Column(name = "amount", updatable = false)
    val amount: Double,

    @Column(name = "description", updatable = false)
    val description: String,

    @Column(name = "payment_date", updatable = false)
    val paymentDate: Instant,

    @Column(name = "approved_at", updatable = false)
    val approvedAt: Instant,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now()
) {

    companion object
}

fun StockEventPostgresModel.Companion.fromStockEvent(stockEvent: StockEvent) = StockEventPostgresModel(
    symbol = stockEvent.symbol,
    type = stockEvent.type.name,
    amount = stockEvent.amount.value.toDouble(),
    description = stockEvent.description,
    paymentDate = stockEvent.paymentDate,
    approvedAt = stockEvent.approvedAt,
)

fun StockEventPostgresModel.toStockEvent() = StockEvent(
    symbol = symbol,
    type = StockEvent.StockEventType.valueOf(type),
    amount = Amount(amount),
    description = description,
    paymentDate = paymentDate,
    approvedAt = approvedAt,
)
