package com.montebruni.holder.wallet.infrastructure.dataprovider

import com.montebruni.holder.account.application.domain.port.CustomerRepository
import com.montebruni.holder.wallet.application.dataprovider.CustomerDataProvider
import com.montebruni.holder.wallet.application.dataprovider.data.CustomerData
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CustomerDataProviderAdapter(
    private val customerRepository: CustomerRepository
) : CustomerDataProvider {

    override fun findById(id: UUID): CustomerData? = customerRepository.findById(id)?.let { CustomerData(it.id) }
}
