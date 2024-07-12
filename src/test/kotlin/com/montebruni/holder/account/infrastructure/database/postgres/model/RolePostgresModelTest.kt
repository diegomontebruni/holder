package com.montebruni.holder.account.infrastructure.database.postgres.model

import com.montebruni.holder.account.domain.entity.Role
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RolePostgresModelTest {

    @Test
    fun `should create model from role`() {
        val role = Role.USER
        val model = RolePostgresModel.from(role)

        assertEquals(role.ordinal.toLong().plus(1), model.id)
        assertEquals(role.name, model.name)
    }

    @Test
    fun `should create role from model`() {
        val model = RolePostgresModel(1, "USER")
        val role = model.toRole()

        assertEquals(model.id, role.ordinal.toLong())
        assertEquals(model.name, role.name)
    }
}
