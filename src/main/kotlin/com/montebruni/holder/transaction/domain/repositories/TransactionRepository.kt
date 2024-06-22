package com.montebruni.holder.transaction.domain.repositories

import com.montebruni.holder.transaction.domain.entity.Transaction

interface TransactionRepository {

    fun save(transaction: Transaction): Transaction
}
