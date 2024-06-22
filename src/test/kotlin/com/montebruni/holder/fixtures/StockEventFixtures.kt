package com.montebruni.holder.fixtures

import com.montebruni.holder.stock.domain.entity.StockEvent
import com.montebruni.holder.stock.domain.valueobject.Amount
import com.montebruni.holder.stock.infrastructure.database.postgres.model.StockEventPostgresModel
import java.time.Instant

fun createStockEvent() = StockEvent(
    ticker = "PETR4",
    type = StockEvent.StockEventType.DIVIDENDO,
    amount = Amount(10.0),
    description = "Dividendo",
    paymentDate = Instant.now(),
    approvedAt = Instant.now()
)

fun createStockEventPostgresModel() = StockEventPostgresModel(
    ticker = "PETR4",
    type = "DIVIDENDO",
    amount = 10.0,
    description = "Dividendo",
    paymentDate = Instant.now(),
    approvedAt = Instant.now()
)
