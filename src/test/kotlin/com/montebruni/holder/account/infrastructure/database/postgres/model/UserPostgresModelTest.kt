package com.montebruni.holder.account.infrastructure.database.postgres.model

import com.montebruni.holder.fixtures.createUserModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserPostgresModelTest {

    @Test
    fun `should create user from model`() {
        val model = createUserModel()
        val user = model.toUser()

        assertEquals(model.id, user.id)
        assertEquals(model.username, user.username)
        assertEquals(model.password, user.password)
        assertEquals(model.status.name, user.status.name)
        assertEquals(model.createdAt, user.createdAt)
    }
}
