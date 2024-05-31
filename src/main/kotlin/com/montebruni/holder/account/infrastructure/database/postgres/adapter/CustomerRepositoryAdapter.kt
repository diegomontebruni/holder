package com.montebruni.holder.account.infrastructure.database.postgres.adapter

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.port.CustomerRepository
import com.montebruni.holder.account.infrastructure.database.postgres.model.CustomerPostgresModel
import com.montebruni.holder.account.infrastructure.database.postgres.model.toCustomer
import com.montebruni.holder.account.infrastructure.database.postgres.repository.CustomerPostgresRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomerRepositoryAdapter(
    private val repository: CustomerPostgresRepository
) : CustomerRepository {

    override fun save(customer: Customer): Customer =
        CustomerPostgresModel
            .fromCustomer(customer)
            .let(repository::save)
            .toCustomer()

    override fun findById(id: UUID): Customer? = repository.findByIdOrNull(id)?.toCustomer()

    override fun findByUserId(userId: UUID): Customer? = repository.findByUserId(userId)?.toCustomer()
}
