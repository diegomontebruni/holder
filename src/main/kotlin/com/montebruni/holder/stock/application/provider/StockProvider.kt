package com.montebruni.holder.stock.application.provider

interface StockProvider {

    fun getByTicker(ticker: String): StockProviderResponse?
}
