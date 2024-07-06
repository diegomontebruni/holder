package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception

import org.springframework.http.HttpStatus

class BrapiBadRequestException(message: String) : BrapiException(HttpStatus.BAD_REQUEST, message)
