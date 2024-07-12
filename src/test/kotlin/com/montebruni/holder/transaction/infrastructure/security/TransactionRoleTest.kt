package com.montebruni.holder.transaction.infrastructure.security

import com.montebruni.holder.account.domain.entity.Role
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod

class TransactionRoleTest {

    @Test
    fun `should return correctly role configuration`() {
        val transactionRole = TransactionRole()
        val roleConfiguration = transactionRole.roleConfiguration

        assert(roleConfiguration.size == 1)
        assert(roleConfiguration[0].url == "/v1/transactions")
        assert(roleConfiguration[0].authorities.containsAll(listOf(Role.USER.name, Role.CUSTOMER.name)))
        assert(roleConfiguration[0].method == HttpMethod.POST)
    }
}
