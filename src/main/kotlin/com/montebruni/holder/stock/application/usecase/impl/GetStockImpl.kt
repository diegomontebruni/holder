package com.montebruni.holder.stock.application.usecase.impl

import com.montebruni.holder.stock.application.event.EventPublisher
import com.montebruni.holder.stock.application.event.events.StockFoundEvent
import com.montebruni.holder.stock.application.provider.StockProvider
import com.montebruni.holder.stock.application.usecase.GetStock
import com.montebruni.holder.stock.application.usecase.input.GetStockInput
import com.montebruni.holder.stock.application.usecase.output.GetStockOutput
import com.montebruni.holder.stock.domain.event.StockFoundEventData
import com.montebruni.holder.stock.domain.repositories.StockRepository
import org.springframework.stereotype.Service

@Service
class GetStockImpl(
    private val stockRepository: StockRepository,
    private val stockProvider: StockProvider,
    private val eventPublisher: EventPublisher
) : GetStock {

    override fun execute(input: GetStockInput): GetStockOutput? =
        stockRepository.findByTicker(input.ticker)
            ?.let { GetStockOutput(it.ticker, it.price, it.name) }
            ?: stockProvider.getByTicker(input.ticker)
                ?.let { GetStockOutput(it.ticker, it.price, it.name) }
                ?.also { sendEvent(it) }

    private fun sendEvent(stock: GetStockOutput) =
        StockFoundEventData(stock.ticker, stock.price, stock.name)
            .let { StockFoundEvent(it) }
            .let { eventPublisher.publishEvent(it) }
}
