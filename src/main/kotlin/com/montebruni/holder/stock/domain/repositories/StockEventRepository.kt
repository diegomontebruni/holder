package com.montebruni.holder.stock.domain.repositories

import com.montebruni.holder.stock.domain.entity.StockEvent

interface StockEventRepository {

    fun save(stockEvent: StockEvent): StockEvent
}
