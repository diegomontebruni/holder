package com.montebruni.holder.transaction.infrastructure.security

import com.montebruni.holder.account.domain.entity.Role
import com.montebruni.holder.infrastructure.configuration.security.RequestRole
import com.montebruni.holder.infrastructure.configuration.security.RoleConfiguration
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class TransactionRole : RequestRole {

    override val roleConfiguration: List<RoleConfiguration>
        get() = listOf(
            RoleConfiguration(listOf(Role.USER.name, Role.CUSTOMER.name), "/v1/transactions", HttpMethod.POST),
        )
}
