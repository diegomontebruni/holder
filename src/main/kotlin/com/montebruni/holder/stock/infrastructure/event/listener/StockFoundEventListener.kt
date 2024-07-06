package com.montebruni.holder.stock.infrastructure.event.listener

import com.montebruni.holder.stock.application.event.events.StockFoundEvent
import com.montebruni.holder.stock.application.usecase.SaveStock
import com.montebruni.holder.stock.application.usecase.input.SaveStockInput
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class StockFoundEventListener(
    private val saveStock: SaveStock
) {

    @EventListener(classes = [StockFoundEvent::class])
    fun handle(event: StockFoundEvent) {
        val eventData = event.data

        SaveStockInput(
            ticker = eventData.ticker,
            name = eventData.name,
            price = eventData.price
        ).let(saveStock::execute)
    }
}
