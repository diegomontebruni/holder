package com.montebruni.holder.account.infrastructure.security

import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod

class AuthRoleTest {

    @Test
    fun `should return correctly role configuration`() {
        val authRole = AuthRole()
        val roleConfiguration = authRole.roleConfiguration

        assert(roleConfiguration.size == 3)
        assert(roleConfiguration[0].url == "/v1/auth/login")
        assert(roleConfiguration[0].method == HttpMethod.POST)

        assert(roleConfiguration[1].url == "/v1/auth/initiate-password-recovery")
        assert(roleConfiguration[1].method == HttpMethod.POST)

        assert(roleConfiguration[2].url == "/v1/auth/recover-password")
        assert(roleConfiguration[2].method == HttpMethod.POST)
    }
}
