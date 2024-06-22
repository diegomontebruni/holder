package com.montebruni.holder.account.infrastructure.database.postgres.model

import com.montebruni.holder.account.domain.entity.Customer
import com.montebruni.holder.account.domain.valueobject.Email
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.UUID

@Entity
@EntityListeners(AuditingEntityListener::class)
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
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now()
) {

    companion object
}

fun CustomerPostgresModel.Companion.fromCustomer(customer: Customer) = CustomerPostgresModel(
    id = customer.id,
    userId = customer.userId,
    name = customer.name,
    email = customer.email.value
)

fun CustomerPostgresModel.toCustomer() = Customer(
    id = id,
    userId = userId,
    name = name,
    email = Email(email),
    createdAt = createdAt
)
