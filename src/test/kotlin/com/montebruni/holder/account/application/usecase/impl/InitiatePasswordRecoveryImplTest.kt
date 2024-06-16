package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.PasswordRecoveryInitiatedEvent
import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.exception.UserNotFoundException
import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.RANDOM_PASSWORD_TOKEN
import com.montebruni.holder.fixtures.createInitiatePasswordRecoveryInput
import com.montebruni.holder.fixtures.createUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant

class InitiatePasswordRecoveryImplTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val encryptorProvider: EncryptorProvider,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: InitiatePasswordRecoveryImpl

    @Test
    fun `should throw exception when user not found`() {
        val input = createInitiatePasswordRecoveryInput()

        every { userRepository.findByUsername(input.username.value) } returns null

        assertThrows<UserNotFoundException> {
            usecase.execute(input)
        }

        verify { userRepository.findByUsername(input.username.value) }
        verify(exactly = 0) {
            encryptorProvider.randomToken()
            userRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should not initiate password recovery when user cannot recover password`() {
        val input = createInitiatePasswordRecoveryInput()
        val user = createUser().copy(
            status = Status.ACTIVE,
            passwordRecoverTokenExpiration = Instant.now().plusSeconds(60)
        )

        every { userRepository.findByUsername(input.username.value) } returns user

        usecase.execute(input)

        verify { userRepository.findByUsername(input.username.value) }
        verify(exactly = 0) {
            encryptorProvider.randomToken()
            userRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should initiate password recovery when given valid input`() {
        val input = createInitiatePasswordRecoveryInput()
        val user = createUser().copy(username = input.username, status = Status.ACTIVE)
        val userSlot = slot<User>()
        val eventSlot = slot<PasswordRecoveryInitiatedEvent>()

        every { userRepository.findByUsername(input.username.value) } returns user
        every { encryptorProvider.randomToken() } returns RANDOM_PASSWORD_TOKEN
        every { userRepository.save(capture(userSlot)) } answers { userSlot.captured }
        justRun { eventPublisher.publishEvent(capture(eventSlot)) }

        usecase.execute(input)

        val userSlotCaptured = userSlot.captured
        assertEquals(user.id, userSlotCaptured.id)
        assertEquals(user.password.value, userSlotCaptured.password.value)
        assertEquals(RANDOM_PASSWORD_TOKEN, userSlotCaptured.passwordRecoverToken?.value)
        assertNotNull(userSlotCaptured.passwordRecoverTokenExpiration)

        val eventSlotData = eventSlot.captured.getData()
        assertEquals(user.username, eventSlotData.username)
        assertEquals(userSlotCaptured.passwordRecoverToken, eventSlotData.passwordRecoverToken)
        assertEquals(userSlotCaptured.passwordRecoverTokenExpiration, eventSlotData.passwordRecoverTokenExpiration)

        verify { userRepository.findByUsername(input.username.value) }
        verify { encryptorProvider.randomToken() }
        verify { userRepository.save(userSlot.captured) }
        verify { eventPublisher.publishEvent(eventSlot.captured) }
    }
}
