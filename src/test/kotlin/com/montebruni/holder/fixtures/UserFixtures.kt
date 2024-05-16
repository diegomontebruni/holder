package com.montebruni.holder.fixtures

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.infrastructure.database.postgres.model.UserPostgresModel
import java.time.Instant
import java.util.UUID

fun createUser() = User(
    id = java.util.UUID.randomUUID(),
    name = "John Snow",
    createdAt = Instant.now()
)

fun createUserModel() = UserPostgresModel(
    id = UUID.randomUUID(),
    name = "John Snow"
)
