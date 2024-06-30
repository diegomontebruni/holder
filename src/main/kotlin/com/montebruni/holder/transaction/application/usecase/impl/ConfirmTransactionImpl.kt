package com.montebruni.holder.transaction.application.usecase.impl

import com.montebruni.holder.transaction.application.usecase.ConfirmTransaction
import com.montebruni.holder.transaction.application.usecase.input.ConfirmTransactionInput
import com.montebruni.holder.transaction.domain.exception.TransactionNotFoundException
import com.montebruni.holder.transaction.domain.repositories.TransactionRepository
import org.springframework.stereotype.Service

@Service
class ConfirmTransactionImpl(
    private val transactionRepository: TransactionRepository
) : ConfirmTransaction {

    override fun execute(input: ConfirmTransactionInput) {
        val transaction = transactionRepository.findById(input.transactionId) ?: throw TransactionNotFoundException()

        transaction
            .toConfirmed()
            ?.let(transactionRepository::save)
    }
}
