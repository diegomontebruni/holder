package com.montebruni.holder.stock.application.usecase.impl

import com.montebruni.holder.stock.application.usecase.SaveStock
import com.montebruni.holder.stock.application.usecase.input.SaveStockInput
import com.montebruni.holder.stock.domain.entity.Stock
import com.montebruni.holder.stock.domain.repositories.StockRepository
import org.springframework.stereotype.Service

@Service
class SaveStockImpl(
    private val stockRepository: StockRepository
) : SaveStock {

    override fun execute(input: SaveStockInput) {
        val stock = stockRepository.findByTicker(input.ticker)
            ?.let { it.update(price = input.price) }
            ?: createStock(input)

        stockRepository.save(stock)
    }

    private fun createStock(input: SaveStockInput) = Stock(
        ticker = input.ticker,
        price = input.price,
        name = input.name
    )
}
