package com.montebruni.holder.account.infrastructure.database.postgres.model

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "users")
class UserPostgresModel(

    @Id
    @Column(updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "username")
    val username: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "status")
    val status: StatusModel = StatusModel.ACTIVE,

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),
) {

    enum class StatusModel { ACTIVE, INACTIVE }
}

fun UserPostgresModel.toUser() = User(
    id = id,
    username = username,
    password = password,
    status = Status.valueOf(status.name),
    createdAt = createdAt
)
