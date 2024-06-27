package com.montebruni.holder.transaction.presentation.rest.exception

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

data class ErrorResponse(
    @Schema(description = "The error code")
    val code: String,
    @Schema(description = "The error message")
    val message: String,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "The list of error details")
    val errors: Collection<String> = emptyList()
)
