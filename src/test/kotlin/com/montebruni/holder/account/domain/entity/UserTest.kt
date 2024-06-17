package com.montebruni.holder.account.domain.entity

import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import com.montebruni.holder.account.domain.exception.UserAlreadyRegisteredException
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.PasswordRecoverToken
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.RANDOM_PASSWORD_TOKEN
import com.montebruni.holder.fixtures.createUser
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.time.Instant

class UserTest : UnitTests() {

    @MockK lateinit var encryptorProvider: EncryptorProvider

    @Nested
    inner class IsPendingCases {

        @Test
        fun `should return true when status is pending`() {
            assertTrue(createUser().isPending())
        }

        @ParameterizedTest
        @EnumSource(Status::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
        fun `should return false when status is different than pending`(status: Status) {
            assertFalse(createUser().copy(status = status).isPending())
        }
    }

    @Nested
    inner class IsActiveCases {

        @Test
        fun `should return true when status is active`() {
            assertTrue(createUser().copy(status = Status.ACTIVE).isActive())
        }

        @ParameterizedTest
        @EnumSource(Status::class, names = ["ACTIVE"], mode = EnumSource.Mode.EXCLUDE)
        fun `should return false when status is different than active`(status: Status) {
            assertFalse(createUser().copy(status = status).isActive())
        }
    }

    @Nested
    inner class canBeRegisteredCases {

        @Test
        fun `should return true when user can be registered`() {
            assertTrue(createUser().canBeRegistered())
        }

        @ParameterizedTest
        @EnumSource(Status::class, names = ["PENDING"], mode = EnumSource.Mode.EXCLUDE)
        fun `should throw exception when user can not be registered`(status: Status) {
            assertThrows<UserAlreadyRegisteredException> { createUser().copy(status = status).canBeRegistered() }
        }
    }

    @Nested
    inner class ActivateCases {

        @Test
        fun `should change status to active`() {
            val user = createUser().copy(status = Status.PENDING).activate()

            assertEquals(Status.ACTIVE, user.status)
        }
    }

    @Nested
    inner class ConstructorCases {

        @Test
        fun `should create user with username and password`() {
            val username = createUser().username
            val password = Password()

            val user = User(username = username, password = password)

            assertEquals(username, user.username)
            assertEquals(password, user.password)
        }

        @Test
        fun `should create user when just username is passed in the constructor`() {
            val username = createUser().username
            val user = User(username = username.value)

            assertEquals(username.value, user.username.value)
            assertEquals(12, user.password.value.length)
        }
    }

    @Nested
    inner class GeneratePasswordRecoverTokenCases {

        @Test
        fun `should generate password recover token with encryptor provider is not null`() {
            val token = RANDOM_PASSWORD_TOKEN

            every { encryptorProvider.randomToken() } returns token

            val user = createUser().generatePasswordRecoverToken(encryptorProvider)

            assertNotNull(user.passwordRecoverToken)
            assertEquals(token, user.passwordRecoverToken?.value)
            assertNotNull(user.passwordRecoverTokenExpiration)

            verify(exactly = 1) { encryptorProvider.randomToken() }
        }

        @Test
        fun `should generate password recover token with encryptor provider is null`() {
            val user = createUser().generatePasswordRecoverToken()

            assertNotNull(user.passwordRecoverToken)
            assertNotNull(user.passwordRecoverTokenExpiration)

            verify(exactly = 0) { encryptorProvider.randomToken() }
        }
    }

    @Nested
    inner class CanInitiateRecoverPassword {

        @Test
        fun `should return true when password recover token is null`() {
            val user = createUser().copy(status = Status.ACTIVE)

            assertTrue(user.canInitiateRecoverPassword())
        }

        @Test
        fun `should return true when password recover token is expired`() {
            val user = createUser().copy(
                status = Status.ACTIVE,
                passwordRecoverToken = PasswordRecoverToken(RANDOM_PASSWORD_TOKEN),
                passwordRecoverTokenExpiration = Instant.now().minusSeconds(3600)
            )

            assertTrue(user.canInitiateRecoverPassword())
        }

        @Test
        fun `should return false when password recover token is not expired`() {
            val user = createUser().copy(
                status = Status.ACTIVE,
                passwordRecoverToken = PasswordRecoverToken(RANDOM_PASSWORD_TOKEN),
                passwordRecoverTokenExpiration = Instant.now().plusSeconds(1)
            )

            assertFalse(user.canInitiateRecoverPassword())
        }

        @Test
        fun `should return false when user is not active`() {
            val user = createUser()

            assertFalse(user.canInitiateRecoverPassword())
        }
    }

    @Nested
    inner class CanRecoverPassword {

        @Test
        fun `should return true when password recover token is valid`() {
            val user = createUser().copy(
                status = Status.ACTIVE,
                passwordRecoverToken = PasswordRecoverToken(RANDOM_PASSWORD_TOKEN),
                passwordRecoverTokenExpiration = Instant.now().plusSeconds(3600)
            )

            assertTrue(user.canRecoverPassword())
        }

        @Test
        fun `should return false when password recover token is expired`() {
            val user = createUser().copy(
                status = Status.ACTIVE,
                passwordRecoverTokenExpiration = Instant.now().minusSeconds(1000)
            )

            assertFalse(user.canRecoverPassword())
        }

        @Test
        fun `should return false when user is not active`() {
            val user = createUser()

            assertFalse(user.canRecoverPassword())
        }

        @Test
        fun `should return false when password recover token is null`() {
            val user = createUser().copy(status = Status.ACTIVE)

            assertFalse(user.canRecoverPassword())
        }
    }
}
