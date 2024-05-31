package com.montebruni.holder.fixtures

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.infrastructure.database.postgres.model.UserPostgresModel
import com.montebruni.holder.account.usecase.input.CreateUserInput
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username
import java.util.UUID

fun createUser() = User(
    id = UUID.randomUUID(),
    username = Username("john.snow"),
    password = Password("password")
)

fun createUserModel() = UserPostgresModel(
    id = UUID.randomUUID(),
    username = "john.snow",
    password = "password",
    status = UserPostgresModel.StatusModel.ACTIVE,
)

fun createUserInput() = CreateUserInput(
    username = "john.snow",
    password = "password",
    managerId = UUID.randomUUID()
)
