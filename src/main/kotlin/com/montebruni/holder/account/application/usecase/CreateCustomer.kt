package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.input.CreateCustomerInput

interface CreateCustomer {

    fun execute(input: CreateCustomerInput)
}
