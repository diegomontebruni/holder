package com.montebruni.holder.account.infrastructure.security

import com.montebruni.holder.account.domain.entity.Role
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod

class AccountRoleTest {

    @Test
    fun `should return correctly role configuration`() {
        val accountRole = AccountRole()
        val roleConfiguration = accountRole.roleConfiguration

        assert(roleConfiguration.size == 3)
        assert(roleConfiguration[0].url == "/v1/customers/complete-registration")
        assert(roleConfiguration[0].authorities.containsAll(listOf(Role.USER.name, Role.CUSTOMER.name)))
        assert(roleConfiguration[0].method == HttpMethod.POST)

        assert(roleConfiguration[1].url == "/v1/users/change-password")
        assert(roleConfiguration[1].authorities.containsAll(listOf(Role.USER.name, Role.CUSTOMER.name)))
        assert(roleConfiguration[1].method == HttpMethod.POST)

        assert(roleConfiguration[2].url == "/v1/users")
        assert(roleConfiguration[2].method == HttpMethod.POST)
    }
}
