package com.montebruni.holder.account.application.usecase

import com.montebruni.holder.account.application.usecase.output.FindUserByIdOutput
import java.util.UUID

interface FindUserById {

    fun execute(id: UUID): FindUserByIdOutput?
}
