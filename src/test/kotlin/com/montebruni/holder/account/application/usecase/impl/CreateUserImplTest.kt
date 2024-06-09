package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.UserCreatedEvent
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.exception.UserAlreadyExistsException
import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createUser
import com.montebruni.holder.fixtures.createUserInput
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateUserImplTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var registerUser: CreateUserImpl

    @Test
    fun `should throw UserAlreadyExistsException when user already exists`() {
        val input = createUserInput()
        val user = createUser()

        every { userRepository.findByUsername(input.username.value) } returns user

        assertThrows<UserAlreadyExistsException> { registerUser.execute(input) }

        verify { userRepository.findByUsername(input.username.value) }
        verify(exactly = 0) {
            userRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should create user`() {
        val input = createUserInput()
        val userSlot = slot<User>()
        val userCreatedEventSlot = slot<UserCreatedEvent>()

        every { userRepository.findByUsername(input.username.value) } returns null
        every { userRepository.save(capture(userSlot)) } answers { userSlot.captured }
        justRun { eventPublisher.publishEvent(capture(userCreatedEventSlot)) }

        val output = registerUser.execute(input)

        val userCaptured = userSlot.captured
        assertEquals(input.username, userCaptured.username)
        assertEquals(12, userCaptured.password.value.length)

        val userCreatedEvent = userCreatedEventSlot.captured.getData()
        assertEquals(userCaptured.id, userCreatedEvent.id)
        assertEquals(userCaptured.username, userCreatedEvent.username)
        assertEquals(userCaptured.password, userCreatedEvent.password)
        assertEquals(userCaptured.status, userCreatedEvent.status)
        assertEquals(input.managerId, userCreatedEvent.managerId)

        assertEquals(output.id, userCaptured.id)
        assertEquals(output.status, userCaptured.status)

        verify {
            userRepository.findByUsername(input.username.value)
            userRepository.save(userSlot.captured)
            eventPublisher.publishEvent(userCreatedEventSlot.captured)
        }
    }

    @Test
    fun `should send event successfully when managerId is null`() {
        val input = createUserInput().copy(managerId = null)
        val userCreatedEventSlot = slot<UserCreatedEvent>()

        every { userRepository.findByUsername(input.username.value) } returns null
        every { userRepository.save(any()) } answers { firstArg() }
        justRun { eventPublisher.publishEvent(capture(userCreatedEventSlot)) }

        registerUser.execute(input)

        assertNull(userCreatedEventSlot.captured.getData().managerId)

        verify {
            userRepository.findByUsername(input.username.value)
            userRepository.save(any())
            eventPublisher.publishEvent(userCreatedEventSlot.captured)
        }
    }
}
