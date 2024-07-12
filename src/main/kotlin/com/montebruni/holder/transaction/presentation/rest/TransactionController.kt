package com.montebruni.holder.transaction.presentation.rest

import com.montebruni.holder.transaction.application.usecase.CreateTransaction
import com.montebruni.holder.transaction.presentation.rest.exception.ErrorResponse
import com.montebruni.holder.transaction.presentation.rest.request.CreateTransactionRequest
import com.montebruni.holder.transaction.presentation.rest.request.toCreateTransactionInput
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/transactions")
class TransactionController(
    private val createTransaction: CreateTransaction
) {

    @Operation(
        summary = "Create a transaction",
        description = "Create a transaction",
        tags = ["Transaction"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Transaction created",
                content = []
            ),
            ApiResponse(
                responseCode = "400",
                description = "Response error",
                content = [
                    Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Server side error",
                content = [
                    Content(
                        mediaType = APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            )
        ]
    )
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun create(@RequestBody request: CreateTransactionRequest) =
        request
            .toCreateTransactionInput()
            .let(createTransaction::execute)
}
