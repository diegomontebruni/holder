package com.montebruni.holder.account.infrastructure.database.postgres

import com.montebruni.holder.account.domain.entity.Role
import com.montebruni.holder.account.infrastructure.database.postgres.model.RolePostgresModel
import com.montebruni.holder.account.infrastructure.database.postgres.model.UserPostgresModel.StatusModel
import com.montebruni.holder.configuration.DatabaseIT
import com.montebruni.holder.fixtures.RANDOM_PASSWORD_TOKEN
import com.montebruni.holder.fixtures.createUserModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
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

        @ParameterizedTest
        @EnumSource(Role::class, mode = EnumSource.Mode.MATCH_ALL)
        fun `should save user with configured roles`(role: Role) {
            val roleModel = RolePostgresModel.from(role)
            val user = createUserModel().copy(roles = setOf(roleModel))
            repository.save(user)

            val result = repository.findByIdOrNull(user.id)

            assertEquals(user.id, result?.id)
            assertEquals(user.username, result?.username)
            assertEquals(user.password, result?.password)
            assertEquals(user.status.name, result?.status?.name)
            assertEquals(roleModel.name, result?.roles?.first()?.name)
            assertEquals(user.createdAt, result?.createdAt)
            assertNull(result?.passwordRecoverToken)
            assertNull(result?.passwordRecoverTokenExpiration)
        }

        @Test
        fun `should save user with diff updated at`() {
            val model = createUserModel().also(repository::saveAndFlush)
            val updatedModel = model.copy(status = StatusModel.PENDING).let(repository::saveAndFlush)

            assertNotEquals(model.updatedAt, updatedModel.updatedAt)
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

    @Nested
    inner class FindByPasswordRecoverTokenCases {

        @Test
        fun `should find user by password recover token`() {
            val user = createUserModel().copy(
                passwordRecoverToken = RANDOM_PASSWORD_TOKEN,
                passwordRecoverTokenExpiration = Instant.now()
            ).also(repository::save)

            val result = repository.findByPasswordRecoverToken(user.passwordRecoverToken!!)

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
            repository.findByPasswordRecoverToken("not-found").let(::assertNull)
        }
    }
}
