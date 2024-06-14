package com.montebruni.holder.account.infrastructure.database

import com.montebruni.holder.account.domain.valueobject.PasswordRecoverToken
import com.montebruni.holder.account.infrastructure.database.postgres.UserPostgresRepository
import com.montebruni.holder.account.infrastructure.database.postgres.model.UserPostgresModel
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.RANDOM_PASSWORD_TOKEN
import com.montebruni.holder.fixtures.createUser
import com.montebruni.holder.fixtures.createUserModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.time.Instant

class UserRepositoryAdapterTest(
    @MockK private val repository: UserPostgresRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: UserRepositoryAdapter

    @Nested
    inner class SaveUserCases {

        @Test
        fun `should save user successfully`() {
            val user = createUser()
            val modelSlot = slot<UserPostgresModel>()

            every { repository.save(capture(modelSlot)) } answers { firstArg() }

            val result = adapter.save(user)

            assertEquals(user.id, modelSlot.captured.id)
            assertEquals(user.username.value, modelSlot.captured.username)
            assertEquals(user.password.value, modelSlot.captured.password)
            assertEquals(user.status.name, modelSlot.captured.status.name)
            assertNull(modelSlot.captured.passwordRecoverToken)
            assertNull(modelSlot.captured.passwordRecoverTokenExpiration)

            assertEquals(user.id, result.id)
            assertEquals(user.username.value, result.username.value)
            assertEquals(user.password.value, result.password.value)
            assertEquals(user.status.name, result.status.name)
            assertNull(result.passwordRecoverToken)
            assertNull(result.passwordRecoverTokenExpiration)

            verify { repository.save(modelSlot.captured) }
        }

        @Test
        fun `should save user when has password recover token`() {
            val token = RANDOM_PASSWORD_TOKEN.let(::PasswordRecoverToken)
            val user = createUser().copy(passwordRecoverToken = token, passwordRecoverTokenExpiration = Instant.now())
            val modelSlot = slot<UserPostgresModel>()

            every { repository.save(capture(modelSlot)) } answers { firstArg() }

            val result = adapter.save(user)

            assertEquals(user.passwordRecoverToken?.value, modelSlot.captured.passwordRecoverToken)
            assertEquals(user.passwordRecoverTokenExpiration, modelSlot.captured.passwordRecoverTokenExpiration)

            assertEquals(user.passwordRecoverToken?.value, result.passwordRecoverToken?.value)
            assertEquals(user.passwordRecoverTokenExpiration, result.passwordRecoverTokenExpiration)

            verify { repository.save(modelSlot.captured) }
        }
    }

    @Nested
    inner class FindByIdCases {

        @Test
        fun `should find user by id`() {
            val userModel = createUserModel()

            every { repository.findByIdOrNull(userModel.id) } returns userModel

            val result = adapter.findById(userModel.id)

            assertNotNull(result)
            assertEquals(userModel.id, result?.id)
            assertEquals(userModel.username, result?.username?.value)
            assertEquals(userModel.password, result?.password?.value)
            assertEquals(userModel.status.name, result?.status?.name)
            assertNull(result?.passwordRecoverToken)
            assertNull(result?.passwordRecoverTokenExpiration)

            verify { repository.findByIdOrNull(userModel.id) }
        }

        @Test
        fun `should find user by id when have nullable properties filled`() {
            val userModel = createUserModel().copy(
                passwordRecoverToken = RANDOM_PASSWORD_TOKEN,
                passwordRecoverTokenExpiration = Instant.now()
            )

            every { repository.findByIdOrNull(userModel.id) } returns userModel

            val result = adapter.findById(userModel.id)

            assertNotNull(result)
            assertEquals(userModel.passwordRecoverToken, result?.passwordRecoverToken?.value)
            assertEquals(userModel.passwordRecoverTokenExpiration, result?.passwordRecoverTokenExpiration)

            verify { repository.findByIdOrNull(userModel.id) }
        }

        @Test
        fun `should return null when not found user by id`() {
            val userModel = createUserModel()

            every { repository.findByIdOrNull(userModel.id) } returns null

            val result = adapter.findById(userModel.id)

            assertNull(result)

            verify { repository.findByIdOrNull(userModel.id) }
        }
    }

    @Nested
    inner class FindByUsernameCases {

        @Test
        fun `should find user by username`() {
            val userModel = createUserModel()

            every { repository.findByUsername(userModel.username) } returns userModel

            val result = adapter.findByUsername(userModel.username)

            assertNotNull(result)
            assertEquals(userModel.id, result?.id)
            assertEquals(userModel.username, result?.username?.value)
            assertEquals(userModel.password, result?.password?.value)
            assertEquals(userModel.status.name, result?.status?.name)
            assertNull(result?.passwordRecoverToken)
            assertNull(result?.passwordRecoverTokenExpiration)

            verify { repository.findByUsername(userModel.username) }
        }

        @Test
        fun `should find user by username when have nullable properties filled`() {
            val userModel = createUserModel().copy(
                passwordRecoverToken = RANDOM_PASSWORD_TOKEN,
                passwordRecoverTokenExpiration = Instant.now()
            )

            every { repository.findByUsername(userModel.username) } returns userModel

            val result = adapter.findByUsername(userModel.username)

            assertNotNull(result)
            assertEquals(userModel.passwordRecoverToken, result?.passwordRecoverToken?.value)
            assertEquals(userModel.passwordRecoverTokenExpiration, result?.passwordRecoverTokenExpiration)

            verify { repository.findByUsername(userModel.username) }
        }

        @Test
        fun `should return null when not found user by username`() {
            val userModel = createUserModel()

            every { repository.findByUsername(userModel.username) } returns null

            val result = adapter.findByUsername(userModel.username)

            assertNull(result)

            verify { repository.findByUsername(userModel.username) }
        }
    }
}
