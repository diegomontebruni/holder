package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.usecase.CreateCustomer
import com.montebruni.holder.account.application.usecase.input.CreateCustomerInput
import com.montebruni.holder.account.application.usecase.input.toCustomer
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.CustomerRepository
import com.montebruni.holder.account.domain.repositories.UserRepository
import org.springframework.stereotype.Service
import kotlin.let

@Service
class CreateCustomerImpl(
    private val userRepository: UserRepository,
    private val customerRepository: CustomerRepository
) : CreateCustomer {

    override fun execute(input: CreateCustomerInput) {
        userRepository.findById(input.userId) ?: throw UserNotFoundException()

        input
            .toCustomer()
            .let(customerRepository::save)
    }
}
