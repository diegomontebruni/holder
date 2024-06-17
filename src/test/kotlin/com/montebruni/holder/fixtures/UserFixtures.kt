package com.montebruni.holder.fixtures

import com.montebruni.holder.account.application.event.events.PasswordRecoveryInitiatedEvent
import com.montebruni.holder.account.application.event.events.UserCreatedEvent
import com.montebruni.holder.account.application.usecase.input.ChangeUserPasswordInput
import com.montebruni.holder.account.application.usecase.input.CompleteUserRegistrationInput
import com.montebruni.holder.account.application.usecase.input.CreateUserInput
import com.montebruni.holder.account.application.usecase.input.InitiatePasswordRecoveryInput
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.PasswordRecoverToken
import com.montebruni.holder.account.domain.valueobject.Username
import com.montebruni.holder.account.infrastructure.database.postgres.model.UserPostgresModel
import com.montebruni.holder.account.presentation.rest.request.ChangeUserPasswordRequest
import com.montebruni.holder.account.presentation.rest.request.CreateUserRequest
import java.time.Instant
import java.util.UUID

const val RANDOM_PASSWORD_TOKEN = "$2a$10\$WVY07SMiMyZbhXUu4f8Bq.avE8j6uR/WVs8Yz6rwerHuJzhJFyKua"
const val ENCRYPTED_PASSWORD =
    "YoQs/HKIO34WfW7mh9ONYg==:\$2a\$10\$EJTYTjTZ/Cvqib7ry8udvO.a0tzZtMdlRihTU/dcKFiZk5RUR1bLS"

fun createUser() = User(
    id = UUID.randomUUID(),
    username = Username("john.snow@winterfell.north"),
    password = Password()
)

fun createUserModel() = UserPostgresModel(
    id = UUID.randomUUID(),
    username = "john.snow.model@winterfell.north",
    password = Password().value,
    status = UserPostgresModel.StatusModel.ACTIVE,
)

fun createUserInput() = CreateUserInput(
    username = Username("john.snow@winterfell.north"),
    managerId = UUID.randomUUID()
)

fun createUserCreatedEvent() = UserCreatedEvent(
    entity = createUser(),
    managerId = UUID.randomUUID()
)

fun createCompleteUserRegistrationInput() = CompleteUserRegistrationInput(
    userId = UUID.randomUUID()
)

fun createCreateUserRequest() = CreateUserRequest(
    username = "john.snow@winterfell.north"
)

fun createChangeUserPasswordInput(password: Password) = ChangeUserPasswordInput(
    username = Username("john.snow@winterfell.north"),
    oldPassword = password,
    newPassword = Password("New-pard1#"),
)

fun createChangeUserPasswordRequest() = ChangeUserPasswordRequest(
    username = "john.snow@winterfell.north",
    oldPassword = "Old-pard1#",
    newPassword = "New-pard1#",
)

fun createInitiatePasswordRecoveryInput() = InitiatePasswordRecoveryInput(
    username = Username("john.snow@winterfell.north")
)

fun createPasswordRecoveryInitiatedEvent() = PasswordRecoveryInitiatedEvent(
    entity = createUser().copy(
        passwordRecoverToken = PasswordRecoverToken(RANDOM_PASSWORD_TOKEN),
        passwordRecoverTokenExpiration = Instant.now().plusSeconds(3600)
    )
)
