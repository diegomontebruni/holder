package com.montebruni.holder.stock.application.usecase

import com.montebruni.holder.stock.application.usecase.input.SaveStockInput

interface SaveStock {

    fun execute(input: SaveStockInput)
}
