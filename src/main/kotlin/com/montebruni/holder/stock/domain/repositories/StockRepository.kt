package com.montebruni.holder.stock.domain.repositories

import com.montebruni.holder.stock.domain.entity.Stock

interface StockRepository {

    fun save(stock: Stock): Stock
    fun findBySymbol(symbol: String): Stock?
}
