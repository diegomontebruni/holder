package com.montebruni.holder.transaction.infrastructure.database

import com.montebruni.holder.transaction.domain.entity.Transaction
import com.montebruni.holder.transaction.domain.repositories.TransactionRepository
import com.montebruni.holder.transaction.infrastructure.database.postgres.TransactionPostgresRepository
import com.montebruni.holder.transaction.infrastructure.database.postgres.model.TransactionPostgresModel
import com.montebruni.holder.transaction.infrastructure.database.postgres.model.fromTransaction
import com.montebruni.holder.transaction.infrastructure.database.postgres.model.toTransaction
import org.springframework.stereotype.Component

@Component
class TransactionRepositoryAdapter(
    private val repository: TransactionPostgresRepository
) : TransactionRepository {

    override fun save(transaction: Transaction): Transaction =
        TransactionPostgresModel
            .fromTransaction(transaction)
            .let(repository::save)
            .toTransaction()
}
