package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.exception.InvalidUserPasswordException
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createChangeUserPasswordInput
import com.montebruni.holder.fixtures.createUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ChangeUserPasswordImplTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val encryptorProvider: EncryptorProvider
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: ChangeUserPasswordImpl

    @Test
    fun `should throw exception when user not found`() {
        val oldPassword = Password("Old-pasrd1#")
        val input = createChangeUserPasswordInput(oldPassword)

        every { userRepository.findByUsername(input.username.value) } returns null

        assertThrows<UserNotFoundException> { usecase.execute(input) }

        verify { userRepository.findByUsername(input.username.value) }
        verify(exactly = 0) {
            encryptorProvider.validate(any(), any())
            userRepository.save(any())
            encryptorProvider.encrypt(any())
        }
    }

    @Test
    fun `should throw exception when old password is invalid`() {
        val oldPassword = Password("Old-passd1#")
        val input = createChangeUserPasswordInput(oldPassword)
        val user = createUser().copy(password = input.oldPassword)

        val oldPasswordSlot = slot<String>()
        val userPasswordSlot = slot<String>()

        every { userRepository.findByUsername(input.username.value) } returns user
        every { encryptorProvider.validate(capture(oldPasswordSlot), capture(userPasswordSlot)) } returns false

        assertThrows<InvalidUserPasswordException> { usecase.execute(input) }

        assertEquals(oldPassword.value, oldPasswordSlot.captured)
        assertEquals(user.password.value, userPasswordSlot.captured)

        verify {
            userRepository.findByUsername(input.username.value)
            encryptorProvider.validate(oldPasswordSlot.captured, userPasswordSlot.captured)
        }
        verify(exactly = 0) {
            userRepository.save(any())
            encryptorProvider.encrypt(any())
        }
    }

    @Test
    fun `should change user password successfully with valid input`() {
        val oldPassword = Password("Old-passd1#")
        val input = createChangeUserPasswordInput(oldPassword)
        val user = createUser().copy(password = input.oldPassword)

        val userSlot = slot<User>()

        every { userRepository.findByUsername(input.username.value) } returns user
        every { encryptorProvider.validate(any(), any()) } returns true
        every { userRepository.save(capture(userSlot)) } answers { userSlot.captured }
        every { encryptorProvider.encrypt(any()) } answers { input.newPassword.value }

        usecase.execute(input)

        assertEquals(input.newPassword.value, userSlot.captured.password.value)

        verify {
            userRepository.findByUsername(input.username.value)
            encryptorProvider.validate(any(), any())
            userRepository.save(userSlot.captured)
            encryptorProvider.encrypt(any())
        }
    }
}
