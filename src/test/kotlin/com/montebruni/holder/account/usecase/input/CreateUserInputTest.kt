package com.montebruni.holder.account.usecase.input

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.fixtures.createUserInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateUserInputTest {

    @Test
    fun `should create user from input`() {
        val input = createUserInput()
        val user = input.toUser()

        assertEquals(input.username, user.username.value)
        assertEquals(input.password, user.password.value)
        assertEquals(Status.PENDING, user.status)
    }
}
