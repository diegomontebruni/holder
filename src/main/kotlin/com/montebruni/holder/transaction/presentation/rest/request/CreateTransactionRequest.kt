package com.montebruni.holder.transaction.presentation.rest.request

import com.montebruni.holder.transaction.application.usecase.input.CreateTransactionInput
import com.montebruni.holder.transaction.domain.entity.Operation
import com.montebruni.holder.transaction.domain.entity.Type
import com.montebruni.holder.transaction.domain.valueobject.Amount
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "Create transaction request")
data class CreateTransactionRequest(
    @Schema(
        description = "The wallet id of the transaction",
        example = "123e4567-e89b-12d3-a456-426614174000",
        required = true
    )
    val walletId: UUID,

    @Schema(
        description = "The ticker of the transaction",
        example = "AAPL",
        required = true
    )
    val ticker: String,

    @Schema(
        description = "The quantity of the transaction",
        example = "10",
        required = true
    )
    val quantity: Int,

    @Schema(
        description = "The value of the transaction",
        required = true
    )
    val value: String,

    @Schema(
        description = "The operation of the transaction",
        required = true
    )
    val operation: Operation,

    @Schema(
        description = "The type of the transaction",
        required = true
    )
    val type: Type,

    @Schema(
        description = "The description of the transaction",
        required = false
    )
    val description: String? = null
)

fun CreateTransactionRequest.toCreateTransactionInput() = CreateTransactionInput(
    walletId = walletId,
    ticker = ticker,
    quantity = quantity,
    value = Amount(value),
    operation = operation,
    type = type,
    description = description
)
