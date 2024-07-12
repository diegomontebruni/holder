package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.input.LoginInput
import com.montebruni.holder.account.application.usecase.output.LoginOutput

interface Login {

    fun execute(input: LoginInput): LoginOutput
}
