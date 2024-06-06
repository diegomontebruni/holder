package com.montebruni.holder.account.infrastructure.database

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.repositories.CustomerRepository
import com.montebruni.holder.account.infrastructure.database.postgres.CustomerPostgresRepository
import com.montebruni.holder.account.infrastructure.database.postgres.model.CustomerPostgresModel
import com.montebruni.holder.account.infrastructure.database.postgres.model.toCustomer
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.let

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
