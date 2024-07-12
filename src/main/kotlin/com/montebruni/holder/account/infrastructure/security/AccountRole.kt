package com.montebruni.holder.account.infrastructure.security

import com.montebruni.holder.infrastructure.configuration.security.RequestRole
import com.montebruni.holder.infrastructure.configuration.security.RoleConfiguration
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class AccountRole : RequestRole {

    override val roleConfiguration: List<RoleConfiguration>
        get() = listOf(
            RoleConfiguration(listOf("USER", "CUSTOMER"), "/v1/customers/complete-registration", HttpMethod.POST),
            RoleConfiguration(listOf("USER", "CUSTOMER"), "/v1/users/change-password", HttpMethod.POST),
            RoleConfiguration(url = "/v1/users", method = HttpMethod.POST)
        )
}
