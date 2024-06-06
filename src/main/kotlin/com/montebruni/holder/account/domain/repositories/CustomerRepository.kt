package com.montebruni.holder.account.domain.repositories

import com.montebruni.holder.account.domain.entity.Customer
import java.util.UUID

interface CustomerRepository {

    fun save(customer: Customer): Customer
    fun findById(id: UUID): Customer?
    fun findByUserId(userId: UUID): Customer?
}
