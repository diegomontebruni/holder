package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.input.CreateCustomerInput
import com.montebruni.holder.account.application.usecase.output.CreateCustomerOutput

interface CreateCustomer {

    fun execute(input: CreateCustomerInput): CreateCustomerOutput
}
