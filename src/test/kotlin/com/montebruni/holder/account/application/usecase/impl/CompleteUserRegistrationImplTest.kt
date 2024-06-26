package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.exception.UserAlreadyRegisteredException
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createCompleteUserRegistrationInput
import com.montebruni.holder.fixtures.createUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CompleteUserRegistrationImplTest(
    @MockK private val userRepository: UserRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: CompleteUserRegistrationImpl

    @Test
    fun `should throw user already registered when user is already registered`() {
        val input = createCompleteUserRegistrationInput()
        val user = createUser().copy(status = Status.ACTIVE)

        every { userRepository.findById(input.userId) } returns user

        assertThrows<UserAlreadyRegisteredException> { usecase.execute(input) }

        verify { userRepository.findById(input.userId) }
        verify(exactly = 0) {
            userRepository.save(any())
        }
    }

    @Test
    fun `should throw user not found exception when user not exists`() {
        val input = createCompleteUserRegistrationInput()

        every { userRepository.findById(input.userId) } returns null

        assertThrows<UserNotFoundException> { usecase.execute(input) }

        verify { userRepository.findById(input.userId) }
        verify(exactly = 0) {
            userRepository.save(any())
        }
    }

    @Test
    fun `should save user when registration successfully`() {
        val input = createCompleteUserRegistrationInput()
        val user = createUser().copy(id = input.userId)
        val repositorySlot = slot<User>()

        every { userRepository.findById(input.userId) } returns user
        every { userRepository.save(capture(repositorySlot)) } answers { repositorySlot.captured }

        usecase.execute(input)

        val userSaved = repositorySlot.captured
        assertEquals(Status.ACTIVE, userSaved.status)

        verify { userRepository.findById(input.userId) }
        verify { userRepository.save(repositorySlot.captured) }
    }
}
