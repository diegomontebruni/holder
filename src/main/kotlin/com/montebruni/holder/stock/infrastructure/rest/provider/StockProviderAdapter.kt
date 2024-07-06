package com.montebruni.holder.stock.infrastructure.rest.provider

import com.montebruni.holder.stock.application.provider.StockProvider
import com.montebruni.holder.stock.application.provider.StockProviderResponse
import com.montebruni.holder.stock.application.provider.exception.StockProviderException
import com.montebruni.holder.stock.domain.valueobject.Amount
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.BrapiHttpClient
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception.BrapiNotFoundException
import org.springframework.stereotype.Component

@Component
class StockProviderAdapter(
    private val brapiHttpClient: BrapiHttpClient
) : StockProvider {

    override fun getByTicker(ticker: String) = runCatching {
        brapiHttpClient.findByTicker(ticker).results.first().let {
            StockProviderResponse(it.ticker, it.name, Amount(it.price))
        }
    }.getOrElse {
        it
            .takeUnless { it is BrapiNotFoundException }
            ?.let { throw StockProviderException(ticker, it) }
    }
}
