package com.montebruni.holder.transaction.application.usecase

import com.montebruni.holder.transaction.application.usecase.input.FailTransactionInput

interface FailTransaction {

    fun execute(input: FailTransactionInput)
}
