package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.input.UpdateCustomerInput

interface UpdateCustomer {

    fun execute(input: UpdateCustomerInput)
}
