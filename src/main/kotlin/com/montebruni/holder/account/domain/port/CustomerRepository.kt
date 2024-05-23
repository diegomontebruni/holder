package com.montebruni.holder.account.domain.port

import com.montebruni.holder.account.domain.entity.Customer
import java.util.UUID

interface CustomerRepository {

    fun findById(id: UUID): Customer?
    fun findByUserId(userId: UUID): Customer?
}
