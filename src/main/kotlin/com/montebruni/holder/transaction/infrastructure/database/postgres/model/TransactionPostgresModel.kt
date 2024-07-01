package com.montebruni.holder.transaction.infrastructure.database.postgres.model

import com.montebruni.holder.transaction.domain.entity.Operation
import com.montebruni.holder.transaction.domain.entity.Status
import com.montebruni.holder.transaction.domain.entity.Transaction
import com.montebruni.holder.transaction.domain.entity.Type
import com.montebruni.holder.transaction.domain.valueobject.Amount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.UUID

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "transaction")
data class TransactionPostgresModel(

    @Id
    @JvmField
    @Column(name = "id", updatable = false)
    val id: UUID,

    @Column(name = "status", nullable = false)
    val status: String,

    @Column(name = "wallet_id", updatable = false)
    val walletId: UUID,

    @Column(name = "ticker", updatable = false)
    val ticker: String,

    @Column(name = "quantity", updatable = false)
    val quantity: Int,

    @Column(name = "value", updatable = false)
    val value: Double,

    @Column(name = "operation", updatable = false)
    val operation: String,

    @Column(name = "type", updatable = false)
    val type: String,

    @Column(name = "description", nullable = true, updatable = false)
    val description: String? = null,

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant = Instant.now(),
) : Persistable<UUID> {

    override fun getId(): UUID? = id
    override fun isNew(): Boolean = status == Status.PENDING.name

    companion object
}

fun TransactionPostgresModel.Companion.fromTransaction(transaction: Transaction): TransactionPostgresModel =
    TransactionPostgresModel(
        id = transaction.id,
        status = transaction.status.name,
        walletId = transaction.walletId,
        ticker = transaction.ticker,
        quantity = transaction.quantity,
        value = transaction.value.value.toDouble(),
        operation = transaction.operation.name,
        type = transaction.type.name,
        description = transaction.description
    )

fun TransactionPostgresModel.toTransaction(): Transaction = Transaction(
    id = id,
    status = Status.valueOf(status),
    walletId = walletId,
    ticker = ticker,
    quantity = quantity,
    value = Amount(value),
    operation = Operation.valueOf(operation),
    type = Type.valueOf(type),
    description = description,
    createdAt = createdAt
)
