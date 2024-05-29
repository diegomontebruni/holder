package com.montebruni.holder.account.infrastructure.database.postgres.model

import com.montebruni.holder.fixtures.createUser
import com.montebruni.holder.fixtures.createUserModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserPostgresModelTest {

    @Test
    fun `should create user from model`() {
        val model = createUserModel()
        val user = model.toUser()

        assertEquals(model.id, user.id)
        assertEquals(model.username, user.username.value)
        assertEquals(model.password, user.password.value)
        assertEquals(model.status.name, user.status.name)
    }

    @Test
    fun `should create model from user`() {
        val user = createUser()
        val model = UserPostgresModel.fromUser(user)

        assertEquals(user.id, model.id)
        assertEquals(user.username.value, model.username)
        assertEquals(user.password.value, model.password)
        assertEquals(user.status.name, model.status.name)
    }
}
