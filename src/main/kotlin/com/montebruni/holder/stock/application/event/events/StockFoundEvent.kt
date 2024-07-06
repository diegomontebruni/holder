package com.montebruni.holder.stock.application.event.events

import com.montebruni.holder.stock.domain.event.StockFoundEventData

data class StockFoundEvent(
    override val data: StockFoundEventData
) : Event
