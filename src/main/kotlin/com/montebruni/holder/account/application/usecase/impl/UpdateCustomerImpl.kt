package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.usecase.UpdateCustomer
import com.montebruni.holder.account.application.usecase.input.UpdateCustomerInput
import com.montebruni.holder.account.domain.exception.CustomerNotFoundException
import com.montebruni.holder.account.domain.repositories.CustomerRepository
import org.springframework.stereotype.Service

@Service
class UpdateCustomerImpl(
    private val customerRepository: CustomerRepository
) : UpdateCustomer {

    override fun execute(input: UpdateCustomerInput) {
        customerRepository.findById(input.id)
            ?.let { it.copy(name = input.name) }
            ?.let(customerRepository::save)
            ?: throw CustomerNotFoundException()
    }
}
