package com.montebruni.holder.transaction.application.usecase

import com.montebruni.holder.transaction.application.usecase.output.FindByIdOutput
import java.util.UUID

interface FindById {

    fun execute(id: UUID): FindByIdOutput?
}
