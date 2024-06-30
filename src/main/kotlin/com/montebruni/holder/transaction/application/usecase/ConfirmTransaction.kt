package com.montebruni.holder.transaction.application.usecase

import com.montebruni.holder.transaction.application.usecase.input.ConfirmTransactionInput

interface ConfirmTransaction {

    fun execute(input: ConfirmTransactionInput)
}
