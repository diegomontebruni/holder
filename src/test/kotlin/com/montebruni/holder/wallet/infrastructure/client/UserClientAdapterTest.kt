package com.montebruni.holder.wallet.infrastructure.client

import com.montebruni.holder.account.presentation.interfaces.UserInterface
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createFindUserByIdResponse
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
    @MockK private val userInterface: UserInterface
) : UnitTests() {

    @InjectMockKs
    private lateinit var adapter: UserClientAdapter

    private val userId = UUID.randomUUID()

    @Test
    fun `should find user by id`() {
        val userResponse = createFindUserByIdResponse().copy(id = userId)

        every { userInterface.findById(userId) } returns userResponse

        val result = adapter.findById(userId)

        assertNotNull(result)
        assertEquals(userId, result?.id)

        verify(exactly = 1) { userInterface.findById(userId) }
    }

    @Test
    fun `should return null when user not found`() {
        every { userInterface.findById(userId) } returns null

        val result = adapter.findById(userId)

        assertNull(result)

        verify(exactly = 1) { userInterface.findById(userId) }
    }
}
