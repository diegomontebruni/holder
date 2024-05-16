package com.montebruni.holder.infrastructure.repository.postgres.model

import com.montebruni.holder.account.infrastructure.database.postgres.model.toUser
import com.montebruni.holder.fixtures.createUserModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserPostgresModelTest {

    @Test
    fun `should create user from model`() {
        val model = createUserModel()
        val user = model.toUser()

        assertEquals(model.id, user.id)
        assertEquals(model.name, user.name)
        assertEquals(model.createdAt, user.createdAt)
    }
}
