package com.montebruni.holder.transaction.application.usecase

import com.montebruni.holder.transaction.application.usecase.input.CreateTransactionInput

interface CreateTransaction {

    fun execute(input: CreateTransactionInput)
}
