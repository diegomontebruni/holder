package com.montebruni.holder.account.infrastructure.security

import com.montebruni.holder.infrastructure.configuration.security.RequestRole
import com.montebruni.holder.infrastructure.configuration.security.RoleConfiguration
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component

@Component
class AuthRole : RequestRole {

    override val roleConfiguration: List<RoleConfiguration>
        get() = listOf(
            RoleConfiguration(url = "/v1/auth/login", method = HttpMethod.POST),
            RoleConfiguration(url = "/v1/auth/initiate-password-recovery", method = HttpMethod.POST),
            RoleConfiguration(url = "/v1/auth/recover-password", method = HttpMethod.POST)
        )
}
