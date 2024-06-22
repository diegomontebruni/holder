package com.montebruni.holder.stock.infrastructure.database

import com.montebruni.holder.stock.domain.entity.Stock
import com.montebruni.holder.stock.domain.repositories.StockRepository
import com.montebruni.holder.stock.infrastructure.database.postgres.StockPostgresRepository
import com.montebruni.holder.stock.infrastructure.database.postgres.model.StockPostgresModel
import com.montebruni.holder.stock.infrastructure.database.postgres.model.fromStock
import com.montebruni.holder.stock.infrastructure.database.postgres.model.toStock
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class StockRepositoryAdapter(
    private val repository: StockPostgresRepository
) : StockRepository {

    override fun save(stock: Stock): Stock =
        StockPostgresModel
            .fromStock(stock)
            .let(repository::save)
            .toStock()

    override fun findByTicker(ticker: String): Stock? = repository.findByIdOrNull(ticker)?.toStock()
}
