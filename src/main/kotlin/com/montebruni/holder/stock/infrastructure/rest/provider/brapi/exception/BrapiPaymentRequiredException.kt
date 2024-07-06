package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception

import org.springframework.http.HttpStatus

class BrapiPaymentRequiredException(message: String) : BrapiException(HttpStatus.PAYMENT_REQUIRED, message)
