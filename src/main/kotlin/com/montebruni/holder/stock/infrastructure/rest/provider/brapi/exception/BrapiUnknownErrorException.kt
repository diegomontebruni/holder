package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception

import org.springframework.http.HttpStatus

class BrapiUnknownErrorException(message: String) : BrapiException(HttpStatus.INTERNAL_SERVER_ERROR, message)
