package com.montebruni.holder.infrastructure.repository.postgres

import com.montebruni.holder.account.infrastructure.database.postgres.repository.UserPostgresRepository
import com.montebruni.holder.configuration.DatabaseIT
import com.montebruni.holder.fixtures.createUserModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class UserPostgresRepositoryIT(
    @Autowired val repository: UserPostgresRepository
) : DatabaseIT(repository) {

    @Test
    fun `should find user by id`() {
        val user = createUserModel().also(repository::save)
        val result = repository.findByIdOrNull(user.id)

        assertEquals(user.id, result?.id)
        assertEquals(user.username, result?.username)
        assertEquals(user.password, result?.password)
        assertEquals(user.status.name, result?.status?.name)
        assertEquals(user.createdAt, result?.createdAt)
    }

    @Test
    fun `should find user by username`() {
        val user = createUserModel().also(repository::save)
        val result = repository.findByUsername(user.username)

        assertEquals(user.id, result?.id)
        assertEquals(user.username, result?.username)
        assertEquals(user.password, result?.password)
        assertEquals(user.status.name, result?.status?.name)
        assertEquals(user.createdAt, result?.createdAt)
    }
}
