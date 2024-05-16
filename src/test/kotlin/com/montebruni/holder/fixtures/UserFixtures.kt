package com.montebruni.holder.fixtures

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.infrastructure.database.postgres.model.UserPostgresModel

fun createUser() = User(
    id = java.util.UUID.randomUUID(),
    name = "John Snow",
    createdAt = java.time.Instant.now()
)

fun createUserModel() = UserPostgresModel(
    id = java.util.UUID.randomUUID(),
    name = "John Snow"
)
