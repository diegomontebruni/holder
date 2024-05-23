package com.montebruni.holder.fixtures

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.infrastructure.database.postgres.model.UserPostgresModel
import java.time.Instant
import java.util.UUID

fun createUser() = User(
    id = UUID.randomUUID(),
    username = "john.snow",
    password = "password",
    status = Status.ACTIVE,
    createdAt = Instant.now()
)

fun createUserModel() = UserPostgresModel(
    id = UUID.randomUUID(),
    username = "john.snow",
    password = "password",
    status = UserPostgresModel.StatusModel.ACTIVE,
)
