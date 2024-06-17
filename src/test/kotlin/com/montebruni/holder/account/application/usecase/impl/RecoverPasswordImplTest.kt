package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.application.event.EventPublisher
import com.montebruni.holder.account.application.event.events.PasswordRecoveredEvent
import com.montebruni.holder.account.application.usecase.input.RecoverPasswordInput
import com.montebruni.holder.account.domain.crypto.EncryptorProvider
import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.exception.InvalidPasswordTokenRecoverException
import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.account.domain.valueobject.PasswordRecoverToken
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.ENCRYPTED_PASSWORD
import com.montebruni.holder.fixtures.createUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant

class RecoverPasswordImplTest(
    @MockK private val userRepository: UserRepository,
    @MockK private val encryptorProvider: EncryptorProvider,
    @MockK private val eventPublisher: EventPublisher
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: RecoverPasswordImpl

    private val input = RecoverPasswordInput("TOKEN")

    @Test
    fun `should throw exception when user not found by password recover token`() {
        every { userRepository.findByPasswordRecoverToken(input.token) } returns null

        assertThrows<InvalidPasswordTokenRecoverException> { usecase.execute(input) }

        verify(exactly = 1) { userRepository.findByPasswordRecoverToken(input.token) }
        verify(exactly = 0) {
            encryptorProvider.encrypt(any())
            userRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should throw exception when user is found but can't recover password`() {
        val user = createUser().copy(
            status = Status.ACTIVE,
            passwordRecoverToken = PasswordRecoverToken(input.token),
            passwordRecoverTokenExpiration = Instant.now().minusSeconds(3600)
        )

        every { userRepository.findByPasswordRecoverToken(input.token) } returns user

        assertThrows<InvalidPasswordTokenRecoverException> { usecase.execute(input) }

        verify(exactly = 1) { userRepository.findByPasswordRecoverToken(input.token) }
        verify(exactly = 0) {
            encryptorProvider.encrypt(any())
            userRepository.save(any())
            eventPublisher.publishEvent(any())
        }
    }

    @Test
    fun `should recover password when given a valid input`() {
        val user = createUser().copy(
            status = Status.ACTIVE,
            passwordRecoverToken = PasswordRecoverToken(input.token),
            passwordRecoverTokenExpiration = Instant.now().plusSeconds(3600)
        )

        val encryptSlot = slot<String>()
        val repositorySlot = slot<User>()
        val eventSlot = slot<PasswordRecoveredEvent>()

        every { userRepository.findByPasswordRecoverToken(input.token) } returns user
        every { encryptorProvider.encrypt(capture(encryptSlot)) } returns ENCRYPTED_PASSWORD
        every { userRepository.save(capture(repositorySlot)) } answers { repositorySlot.captured }
        every { eventPublisher.publishEvent(capture(eventSlot)) } answers { eventSlot.captured }

        usecase.execute(input)

        val userCaptured = repositorySlot.captured
        assertEquals(ENCRYPTED_PASSWORD, userCaptured.password.value)
        assertNotEquals(user.passwordRecoverTokenExpiration, userCaptured.passwordRecoverTokenExpiration)
        assertNotEquals(user.password.value, userCaptured.password.value)

        val eventData = eventSlot.captured.getData()
        assertEquals(user.username.value, eventData.username?.value)
        assertEquals(encryptSlot.captured, eventData.password?.value)

        verify(exactly = 1) {
            userRepository.findByPasswordRecoverToken(input.token)
            encryptorProvider.encrypt(encryptSlot.captured)
            userRepository.save(repositorySlot.captured)
            eventPublisher.publishEvent(eventSlot.captured)
        }
    }
}
