package com.montebruni.holder.stock.infrastructure.rest.provider

import com.montebruni.holder.stock.application.provider.StockProvider
import com.montebruni.holder.stock.application.provider.StockProviderResponse
import com.montebruni.holder.stock.domain.valueobject.Amount
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.BrapiHttpClient
import org.springframework.stereotype.Component

@Component
class StockProviderAdapter(
    private val brapiHttpClient: BrapiHttpClient
) : StockProvider {

    override fun getByTicker(ticker: String) = brapiHttpClient.findByTicker(ticker).results.firstOrNull()?.let {
        StockProviderResponse(
            ticker = it.ticker,
            name = it.name,
            price = Amount(it.price)
        )
    }
}
