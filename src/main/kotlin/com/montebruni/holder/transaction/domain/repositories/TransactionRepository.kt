package com.montebruni.holder.transaction.domain.repositories

import com.montebruni.holder.transaction.domain.entity.Transaction
import java.util.UUID

interface TransactionRepository {

    fun save(transaction: Transaction): Transaction
    fun findById(id: UUID): Transaction?
}
