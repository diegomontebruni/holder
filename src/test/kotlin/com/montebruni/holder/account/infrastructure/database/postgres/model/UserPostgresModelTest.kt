package com.montebruni.holder.account.infrastructure.database.postgres.model

import com.montebruni.holder.account.domain.valueobject.PasswordRecoverToken
import com.montebruni.holder.fixtures.RANDOM_PASSWORD_TOKEN
import com.montebruni.holder.fixtures.createUser
import com.montebruni.holder.fixtures.createUserModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant

class UserPostgresModelTest {

    @Test
    fun `should create user from model`() {
        val model = createUserModel().copy(
            passwordRecoverToken = RANDOM_PASSWORD_TOKEN,
            passwordRecoverTokenExpiration = Instant.now()
        )
        val user = model.toUser()

        assertEquals(model.id, user.id)
        assertEquals(model.username, user.username.value)
        assertEquals(model.password, user.password.value)
        assertEquals(model.status.name, user.status.name)
        assertEquals(model.passwordRecoverToken, user.passwordRecoverToken?.value)
        assertEquals(model.passwordRecoverTokenExpiration, user.passwordRecoverTokenExpiration)
    }

    @Test
    fun `should create model from user`() {
        val user = createUser().copy(
            passwordRecoverToken = RANDOM_PASSWORD_TOKEN.let(::PasswordRecoverToken),
            passwordRecoverTokenExpiration = Instant.now()
        )
        val model = UserPostgresModel.fromUser(user)

        assertEquals(user.id, model.id)
        assertEquals(user.username.value, model.username)
        assertEquals(user.password.value, model.password)
        assertEquals(user.status.name, model.status.name)
        assertEquals(user.passwordRecoverToken?.value, model.passwordRecoverToken)
        assertEquals(user.passwordRecoverTokenExpiration, model.passwordRecoverTokenExpiration)
    }
}
