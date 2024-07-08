package com.montebruni.holder.transaction.application.usecase

import com.montebruni.holder.transaction.application.usecase.output.FindTransactionByIdOutput
import java.util.UUID

interface FindTransactionById {

    fun execute(id: UUID): FindTransactionByIdOutput?
}
