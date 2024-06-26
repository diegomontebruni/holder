package com.montebruni.holder.transaction.infrastructure.client

import com.montebruni.holder.stock.domain.repositories.StockRepository
import com.montebruni.holder.transaction.application.client.StockClient
import org.springframework.stereotype.Component

@Component
class StockClientAdapter(
    private val stockRepository: StockRepository
) : StockClient {

    override fun existsByTicker(ticker: String): Boolean = stockRepository.findByTicker(ticker)?.let { true } == true
}
