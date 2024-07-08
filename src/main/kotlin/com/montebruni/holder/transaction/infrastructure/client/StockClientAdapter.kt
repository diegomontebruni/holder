package com.montebruni.holder.transaction.infrastructure.client

import com.montebruni.holder.stock.presentation.interfaces.StockInterface
import com.montebruni.holder.transaction.application.client.StockClient
import org.springframework.stereotype.Component

@Component
class StockClientAdapter(
    private val stockInterface: StockInterface
) : StockClient {

    override fun existsByTicker(ticker: String): Boolean =
        stockInterface.getStockByTicker(ticker)?.let { true } == true
}
