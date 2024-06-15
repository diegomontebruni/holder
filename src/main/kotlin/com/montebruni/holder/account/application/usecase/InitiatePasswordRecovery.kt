package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.input.InitiatePasswordRecoveryInput

interface InitiatePasswordRecovery {

    fun execute(input: InitiatePasswordRecoveryInput)
}
