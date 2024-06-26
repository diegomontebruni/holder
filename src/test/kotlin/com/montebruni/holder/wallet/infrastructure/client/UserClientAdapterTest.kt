package com.montebruni.holder.wallet.infrastructure.client

import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID

class UserClientAdapterTest(
    @MockK private val userRepository: UserRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: UserClientAdapter

    private val userId = UUID.randomUUID()

    @Test
    fun `should find user by id`() {
        val customer = createUser().copy(id = userId)

        every { userRepository.findById(userId) } returns customer

        val result = adapter.findById(userId)

        assertNotNull(result)
        assertEquals(userId, result?.id)

        verify(exactly = 1) { userRepository.findById(userId) }
    }

    @Test
    fun `should return null when user not found`() {
        every { userRepository.findById(userId) } returns null

        val result = adapter.findById(userId)

        assertNull(result)

        verify(exactly = 1) { userRepository.findById(userId) }
    }
}
