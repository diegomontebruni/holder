package com.montebruni.holder.stock.infrastructure.database

import com.montebruni.holder.stock.domain.entity.StockEvent
import com.montebruni.holder.stock.domain.repositories.StockEventRepository
import com.montebruni.holder.stock.infrastructure.database.postgres.StockEventPostgresRepository
import com.montebruni.holder.stock.infrastructure.database.postgres.model.StockEventPostgresModel
import com.montebruni.holder.stock.infrastructure.database.postgres.model.fromStockEvent
import com.montebruni.holder.stock.infrastructure.database.postgres.model.toStockEvent
import org.springframework.stereotype.Component

@Component
class StockEventRepositoryAdapter(
    private val repository: StockEventPostgresRepository
) : StockEventRepository {

    override fun save(stockEvent: StockEvent) =
        StockEventPostgresModel
            .fromStockEvent(stockEvent)
            .let(repository::save)
            .toStockEvent()
}
