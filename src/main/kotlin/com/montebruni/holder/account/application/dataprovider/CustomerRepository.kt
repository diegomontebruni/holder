package com.montebruni.holder.account.application.dataprovider

import com.montebruni.holder.account.application.domain.entity.Customer
import java.util.UUID

interface CustomerRepository {

    fun save(customer: Customer): Customer
    fun findById(id: UUID): Customer?
    fun findByUserId(userId: UUID): Customer?
}
