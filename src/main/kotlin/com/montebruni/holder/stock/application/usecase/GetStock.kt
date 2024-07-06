package com.montebruni.holder.stock.application.usecase

import com.montebruni.holder.stock.application.usecase.input.GetStockInput
import com.montebruni.holder.stock.application.usecase.output.GetStockOutput

interface GetStock {

    fun execute(input: GetStockInput): GetStockOutput?
}
