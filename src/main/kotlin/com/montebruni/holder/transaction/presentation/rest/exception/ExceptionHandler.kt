package com.montebruni.holder.transaction.presentation.rest.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(
    name = "TransactionExceptionHandler",
    basePackages = ["com.montebruni.holder.transaction.presentation.rest"]
)
class ExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException) = ResponseEntity
        .badRequest()
        .body(
            ErrorResponse(
                code = ex.javaClass.simpleName,
                message = ex.localizedMessage,
                errors = listOf(ex.message ?: "")
            )
        )

    @ExceptionHandler(Exception::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(ex: Exception) = ResponseEntity
        .internalServerError()
        .body(
            ErrorResponse(
                code = ex.javaClass.simpleName,
                message = ex.localizedMessage,
                errors = listOf(ex.message ?: "")
            )
        )
}
