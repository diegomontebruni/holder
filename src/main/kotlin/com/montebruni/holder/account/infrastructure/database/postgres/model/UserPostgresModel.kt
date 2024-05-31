package com.montebruni.holder.account.infrastructure.database.postgres.model

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.Username
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "users")
data class UserPostgresModel(

    @Id
    @Column(updatable = false)
    val id: UUID,

    @Column(name = "username")
    val username: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: StatusModel,

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),
) {

    enum class StatusModel { PENDING, ACTIVE, INACTIVE }

    companion object {

        fun fromUser(user: User) = UserPostgresModel(
            id = user.id,
            username = user.username.value,
            password = user.password.value,
            status = StatusModel.valueOf(user.status.name)
        )
    }
}

fun UserPostgresModel.toUser() = User(
    id = id,
    username = Username(username),
    password = Password(password),
    status = Status.valueOf(status.name)
)
