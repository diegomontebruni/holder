package com.montebruni.holder.account.infrastructure.database.postgres.model

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.valueobject.Password
import com.montebruni.holder.account.domain.valueobject.PasswordRecoverToken
import com.montebruni.holder.account.domain.valueobject.Username
import com.montebruni.holder.account.infrastructure.database.postgres.model.UserPostgresModel.StatusModel
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.UUID

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "users")
data class UserPostgresModel(

    @Id
    @Column(updatable = false)
    val id: UUID,

    @Column(name = "username", updatable = false)
    val username: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: StatusModel,

    @Column(name = "password_recover_token")
    val passwordRecoverToken: String? = null,

    @Column(name = "password_recover_token_expiration")
    val passwordRecoverTokenExpiration: Instant? = null,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    val roles: Set<RolePostgresModel>?,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now()
) {

    enum class StatusModel { PENDING, ACTIVE, INACTIVE }

    companion object {

        fun fromUser(user: User) = UserPostgresModel(
            id = user.id,
            username = user.username.value,
            password = user.password.value,
            status = StatusModel.valueOf(user.status.name),
            roles = user.roles.map { RolePostgresModel.from(it) }.toSet(),
            passwordRecoverToken = user.passwordRecoverToken?.value,
            passwordRecoverTokenExpiration = user.passwordRecoverTokenExpiration,
        )
    }
}

fun UserPostgresModel.toUser() = User(
    id = id,
    username = Username(username),
    password = Password(password),
    status = Status.valueOf(status.name),
    roles = roles?.map(RolePostgresModel::toRole)?.toSet() ?: throw IllegalArgumentException("Roles cannot be null"),
    passwordRecoverToken = passwordRecoverToken?.let(::PasswordRecoverToken),
    passwordRecoverTokenExpiration = passwordRecoverTokenExpiration,
)
