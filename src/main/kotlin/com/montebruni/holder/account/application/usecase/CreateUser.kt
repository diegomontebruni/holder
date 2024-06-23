package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.input.CreateUserInput

interface CreateUser {

    fun execute(input: CreateUserInput)
}
