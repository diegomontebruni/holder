package com.montebruni.holder.account.presentation.interfaces

import com.montebruni.holder.account.application.usecase.FindUserById
import com.montebruni.holder.account.application.usecase.output.FindUserByIdOutput
import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.valueobject.Username
import com.montebruni.holder.configuration.UnitTests
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.util.UUID

class UserInterfaceTest(
    @MockK private val findUserById: FindUserById
) : UnitTests() {

    @InjectMockKs
    private lateinit var userInterface: UserInterface

    @Test
    fun `should return output when find user by id`() {
        val id = UUID.randomUUID()
        val output = FindUserByIdOutput(
            id = id,
            username = Username("d@d.com"),
            status = Status.ACTIVE
        )

        every { findUserById.execute(id) } returns output

        val response = userInterface.findById(id)

        assertEquals(id, response?.id)
        assertEquals(output.username.value, response?.username)
        assertEquals(output.status.name, response?.status)

        verify(exactly = 1) { findUserById.execute(id) }
    }

    @Test
    fun `should return null when find user by id`() {
        val id = UUID.randomUUID()

        every { findUserById.execute(id) } returns null

        assertNull(userInterface.findById(id))
    }
}
