package com.montebruni.holder.transaction.application.client

interface StockClient {

    fun existsByTicker(ticker: String): Boolean
}
