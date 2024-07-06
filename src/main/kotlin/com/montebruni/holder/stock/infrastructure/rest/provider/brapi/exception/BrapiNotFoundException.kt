package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception

import org.springframework.http.HttpStatus

class BrapiNotFoundException(message: String) : BrapiException(HttpStatus.NOT_FOUND, message)
