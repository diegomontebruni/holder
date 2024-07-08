package com.montebruni.holder.transaction.application.usecase.impl

import com.montebruni.holder.transaction.application.usecase.FindById
import com.montebruni.holder.transaction.application.usecase.output.FindByIdOutput
import com.montebruni.holder.transaction.domain.repositories.TransactionRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FindByIdImpl(
    private val transactionRepository: TransactionRepository
) : FindById {

    override fun execute(id: UUID): FindByIdOutput? = transactionRepository.findById(id)?.let {
        FindByIdOutput(
            id = it.id,
            status = it.status,
            walletId = it.walletId,
            ticker = it.ticker,
            quantity = it.quantity,
            value = it.value,
            operation = it.operation,
            type = it.type,
            description = it.description,
            createdAt = it.createdAt
        )
    }
}
