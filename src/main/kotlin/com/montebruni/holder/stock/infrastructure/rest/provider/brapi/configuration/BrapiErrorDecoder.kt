package com.montebruni.holder.stock.infrastructure.rest.provider.brapi.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception.BrapiBadRequestException
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception.BrapiException.BrapiErrorMessage
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception.BrapiNotFoundException
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception.BrapiPaymentRequiredException
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception.BrapiUnauthorizedException
import com.montebruni.holder.stock.infrastructure.rest.provider.brapi.exception.BrapiUnknownErrorException
import feign.FeignException
import feign.Response
import feign.RetryableException
import feign.codec.ErrorDecoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import java.lang.Exception

class BrapiErrorDecoder(
    @Autowired private val mapper: ObjectMapper
) : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception {
        val brapiException = mapBrapiException(FeignException.errorStatus(methodKey, response))

        return when {
            shouldRetry(response) -> RetryableException(
                response.status(),
                methodKey,
                response.request().httpMethod(),
                brapiException,
                null as Long?,
                response.request()
            )
            else -> brapiException
        }
    }

    private fun mapBrapiException(feignException: FeignException): Exception {
        val message = brapiErrorMessageDecode(feignException).message

        return when (feignException.status()) {
            HttpStatus.BAD_REQUEST.value() -> BrapiBadRequestException(message)
            HttpStatus.UNAUTHORIZED.value() -> BrapiUnauthorizedException(message)
            HttpStatus.PAYMENT_REQUIRED.value() -> BrapiPaymentRequiredException(message)
            HttpStatus.NOT_FOUND.value() -> BrapiNotFoundException(message)
            else -> BrapiUnknownErrorException(message)
        }
    }

    private fun brapiErrorMessageDecode(feignException: FeignException) = runCatching {
        mapper.readValue<BrapiErrorMessage>(feignException.contentUTF8())
    }.getOrElse { BrapiErrorMessage("unknown error message") }

    private fun shouldRetry(response: Response): Boolean = HttpStatus.valueOf(response.status()) in listOf(
        HttpStatus.INTERNAL_SERVER_ERROR
    )
}
