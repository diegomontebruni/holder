package com.montebruni.holder.wallet.infrastructure.client

import com.montebruni.holder.account.domain.repositories.CustomerRepository
import com.montebruni.holder.wallet.application.client.CustomerClient
import com.montebruni.holder.wallet.application.client.response.CustomerResponse
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomerClientAdapter(
    private val customerRepository: CustomerRepository
) : CustomerClient {

    override fun findById(id: UUID) = customerRepository.findById(id)?.let { CustomerResponse(it.id) }
}
