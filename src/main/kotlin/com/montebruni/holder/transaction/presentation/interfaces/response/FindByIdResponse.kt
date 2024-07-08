package com.montebruni.holder.transaction.presentation.interfaces.response

import java.time.Instant
import java.util.UUID

data class FindByIdResponse(
    val id: UUID,
    val status: String,
    val walletId: UUID,
    val ticker: String,
    val quantity: Int,
    val value: Double,
    val operation: String,
    val type: String,
    val description: String?,
    val createdAt: Instant?
)
