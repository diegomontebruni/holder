package com.montebruni.holder.account.application.usecase.impl

import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.configuration.UnitTests
import com.montebruni.holder.fixtures.createUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID

class FindUserByIdImplTest(
    @MockK private val userRepository: UserRepository
) : UnitTests() {

    @InjectMockKs
    private lateinit var usecase: FindUserByIdImpl

    @Test
    fun `should return output when find user by id`() {
        val id = UUID.randomUUID()
        val user = createUser().copy(id = id)

        every { userRepository.findById(id) } returns user

        val response = usecase.execute(id)

        assertEquals(id, response?.id)
        assertEquals(user.username, response?.username)
        assertEquals(user.status, response?.status)

        verify(exactly = 1) { userRepository.findById(id) }
    }

    @Test
    fun `should return null when user not found by id`() {
        val id = UUID.randomUUID()

        every { userRepository.findById(id) } returns null

        assertNull(usecase.execute(id))

        verify(exactly = 1) { userRepository.findById(id) }
    }
}
