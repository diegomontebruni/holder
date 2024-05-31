package com.montebruni.holder.account.infrastructure.database.postgres.model

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.valueobject.Email
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "customer")
data class CustomerPostgresModel(

    @Id
    @Column(updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "name")
    val name: String? = null,

    @Column(name = "email", nullable = false)
    val email: String,

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),
)

fun CustomerPostgresModel.toCustomer() = Customer(
    id = id,
    userId = userId,
    name = name,
    email = Email(email),
    createdAt = createdAt
)
