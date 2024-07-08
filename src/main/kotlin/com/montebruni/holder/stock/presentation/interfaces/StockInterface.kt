package com.montebruni.holder.stock.presentation.interfaces

import com.montebruni.holder.stock.application.usecase.GetStock
import com.montebruni.holder.stock.application.usecase.input.GetStockInput
import com.montebruni.holder.stock.presentation.interfaces.response.GetStockByTickerResponse
import org.springframework.stereotype.Component

@Component
class StockInterface(
    private val getStock: GetStock
) {

    fun getStockByTicker(ticker: String): GetStockByTickerResponse? =
        GetStockInput(ticker)
            .let(getStock::execute)
            ?.let {
                GetStockByTickerResponse(
                    ticker = it.ticker,
                    name = it.name,
                    price = it.price.value.toDouble()
                )
            }
}
