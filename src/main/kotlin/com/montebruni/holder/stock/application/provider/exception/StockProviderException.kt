package com.montebruni.holder.stock.application.provider.exception

class StockProviderException(
    ticker: String,
    cause: Throwable?
) : Exception("Error getting stock by ticker $ticker", cause)
