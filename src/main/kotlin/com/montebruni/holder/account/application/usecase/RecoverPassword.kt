package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.input.RecoverPasswordInput

interface RecoverPassword {

    fun execute(input: RecoverPasswordInput)
}
