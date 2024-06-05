package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.input.CreateUserInput
import com.montebruni.holder.account.application.usecase.output.CreateUserOutput

interface CreateUser {

    fun execute(input: CreateUserInput): CreateUserOutput
}
