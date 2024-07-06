package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception

import org.springframework.http.HttpStatus

@Suppress("UnusedPrivateProperty")
open class BrapiException(
    httpStatus: HttpStatus,
    message: String
) : RuntimeException(message) {

    data class BrapiErrorMessage(
        val message: String
    )
}
