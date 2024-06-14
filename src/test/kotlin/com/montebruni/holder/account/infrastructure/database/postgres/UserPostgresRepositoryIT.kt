package com.montebruni.holder.account.infrastructure.database.postgres

import com.montebruni.holder.configuration.DatabaseIT
import com.montebruni.holder.fixtures.RANDOM_PASSWORD_TOKEN
import com.montebruni.holder.fixtures.createUserModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.time.Instant
import java.util.UUID
import kotlin.also
import kotlin.let

class UserPostgresRepositoryIT(
    @Autowired val repository: UserPostgresRepository
) : DatabaseIT(repository) {

    @Nested
    inner class SaveCases {

        @Test
        fun `should save user with nullable properties`() {
            val user = createUserModel()
            repository.save(user)

            val result = repository.findByIdOrNull(user.id)

            assertEquals(user.id, result?.id)
            assertEquals(user.username, result?.username)
            assertEquals(user.password, result?.password)
            assertEquals(user.status.name, result?.status?.name)
            assertEquals(user.createdAt, result?.createdAt)
            assertNull(result?.passwordRecoverToken)
            assertNull(result?.passwordRecoverTokenExpiration)
        }
    }

    @Nested
    inner class FindByIdCases {

        @Test
        fun `should find user by id`() {
            val user = createUserModel().copy(
                passwordRecoverToken = RANDOM_PASSWORD_TOKEN,
                passwordRecoverTokenExpiration = Instant.now()
            ).also(repository::save)

            val result = repository.findByIdOrNull(user.id)

            assertEquals(user.id, result?.id)
            assertEquals(user.username, result?.username)
            assertEquals(user.password, result?.password)
            assertEquals(user.status.name, result?.status?.name)
            assertEquals(user.passwordRecoverToken, result?.passwordRecoverToken)
            assertEquals(user.passwordRecoverTokenExpiration, result?.passwordRecoverTokenExpiration)
            assertEquals(user.createdAt, result?.createdAt)
        }

        @Test
        fun `should return null when user not found`() {
            repository.findByIdOrNull(UUID.randomUUID()).let(::assertNull)
        }
    }

    @Nested
    inner class FindByUsernameCases {

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

        @Test
        fun `should return null when user not found`() {
            repository.findByUsername("not-found").let(::assertNull)
        }
    }
}
