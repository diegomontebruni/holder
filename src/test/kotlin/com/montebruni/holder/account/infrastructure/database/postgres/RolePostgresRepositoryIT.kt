package com.montebruni.holder.account.infrastructure.database.postgres

import com.montebruni.holder.account.domain.entity.Role
import com.montebruni.holder.configuration.DatabaseIT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class RolePostgresRepositoryIT(
    @Autowired val repository: RolePostgresRepository
) : DatabaseIT() {

    @Test
    fun `should count the correctly quantity of existing roles`() {
        assertEquals(Role.entries.size.toLong(), repository.count())
    }
}
