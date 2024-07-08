package com.montebruni.holder.transaction.application.usecase.impl

import com.montebruni.holder.transaction.application.usecase.FindTransactionById
import com.montebruni.holder.transaction.application.usecase.output.FindTransactionByIdOutput
import com.montebruni.holder.transaction.domain.repositories.TransactionRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FindTransactionByIdImpl(
    private val transactionRepository: TransactionRepository
) : FindTransactionById {

    override fun execute(id: UUID): FindTransactionByIdOutput? = transactionRepository.findById(id)?.let {
        FindTransactionByIdOutput(
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
