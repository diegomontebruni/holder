package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import com.montebruni.holder.account.domain.crypto.TokenProvider
import com.montebruni.holder.account.domain.crypto.data.Token
import com.montebruni.holder.account.domain.crypto.data.TokenConfiguration
import com.montebruni.holder.account.domain.entity.Role
import com.montebruni.holder.account.domain.exception.InvalidCredentialsException
import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createLoginInput
import com.montebruni.holder.fixtures.createUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LoginImplTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val encryptorProvider: EncryptorProvider,
    @MockK private val tokenProvider: TokenProvider
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: LoginImpl

    @Test
    fun `should throw invalid credentials exception when user not found`() {
        val input = createLoginInput()

        every { userRepository.findByUsername(input.username.value) } returns null

        assertThrows<InvalidCredentialsException> { usecase.execute(input) }

        verify(exactly = 1) { userRepository.findByUsername(input.username.value) }
        verify(exactly = 0) {
            encryptorProvider.encrypt(any())
            tokenProvider.encode(any())
        }
    }

    @Test
    fun `should throw invalid credentials exception when password is invalid`() {
        val input = createLoginInput()
        val user = createUser().copy(username = input.username)

        every { userRepository.findByUsername(input.username.value) } returns user
        every { encryptorProvider.validate(any(), any()) } returns false

        assertThrows<InvalidCredentialsException> { usecase.execute(input) }

        verify(exactly = 1) {
            userRepository.findByUsername(input.username.value)
            encryptorProvider.validate(any(), any())
        }
        verify(exactly = 0) { tokenProvider.encode(any()) }
    }

    @Test
    fun `should return login output when user is found and password is valid`() {
        val input = createLoginInput()
        val user = createUser().copy(username = input.username, roles = setOf(Role.USER))

        val encodeSlot = slot<TokenConfiguration>()
        val token = Token(value = "TOKEN", expiresAt = 123456789L)

        every { userRepository.findByUsername(input.username.value) } returns user
        every { encryptorProvider.validate(any(), any()) } returns true
        every { tokenProvider.encode(capture(encodeSlot)) } returns token

        val output = usecase.execute(input)

        val tokenConfiguration = encodeSlot.captured
        assertEquals(user.id.toString(), tokenConfiguration.subject)
        assertEquals(user.roles.first().name, tokenConfiguration.roles.first())

        assertEquals(token.value, output.accessToken)
        assertEquals(token.expiresAt, output.expiresAt)

        verify(exactly = 1) {
            userRepository.findByUsername(input.username.value)
            encryptorProvider.validate(any(), any())
            tokenProvider.encode(any())
        }
    }
}
