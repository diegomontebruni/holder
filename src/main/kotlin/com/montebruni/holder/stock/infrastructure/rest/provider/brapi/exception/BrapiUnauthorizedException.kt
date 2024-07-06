package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception

import org.springframework.http.HttpStatus

class BrapiUnauthorizedException(message: String) : BrapiException(HttpStatus.UNAUTHORIZED, message)
