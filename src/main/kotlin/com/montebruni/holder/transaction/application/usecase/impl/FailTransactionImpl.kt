package com.montebruni.holder.transaction.application.usecase.impl

import com.montebruni.holder.transaction.application.usecase.FailTransaction
import com.montebruni.holder.transaction.application.usecase.input.FailTransactionInput
import com.montebruni.holder.transaction.domain.exception.TransactionNotFoundException
import com.montebruni.holder.transaction.domain.repositories.TransactionRepository
import org.springframework.stereotype.Service

@Service
class FailTransactionImpl(
    private val transactionRepository: TransactionRepository
) : FailTransaction {

    override fun execute(input: FailTransactionInput) {
        val transaction = transactionRepository.findById(input.transactionId) ?: throw TransactionNotFoundException()

        transaction
            .toFailed()
            ?.let(transactionRepository::save)
    }
}
