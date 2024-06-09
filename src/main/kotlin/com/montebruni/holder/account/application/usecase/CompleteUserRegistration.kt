package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.input.CompleteUserRegistrationInput

interface CompleteUserRegistration {

    fun execute(input: CompleteUserRegistrationInput)
}
