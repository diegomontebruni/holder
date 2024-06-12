package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.input.ChangeUserPasswordInput

interface ChangeUserPassword {

    fun execute(input: ChangeUserPasswordInput)
}
