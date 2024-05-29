package com.montebruni.holder.account.usecase.output

import com.montebruni.holder.fixtures.createUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateUserOutputTest {

    @Test
    fun `should create user output from user`() {
        val user = createUser()
        val output = CreateUserOutput.fromUser(user)

        assertEquals(user.id, output.id)
        assertEquals(user.status, output.status)
    }
}
