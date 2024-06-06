package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.input.CompleteCustomerRegistrationInput

interface CompleteCustomerRegistration {

    fun execute(input: CompleteCustomerRegistrationInput)
}
