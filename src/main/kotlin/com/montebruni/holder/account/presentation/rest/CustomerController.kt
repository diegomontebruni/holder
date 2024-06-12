package com.montebruni.holder.account.presentation.rest

import com.montebruni.holder.account.application.usecase.CompleteCustomerRegistration
import com.montebruni.holder.account.presentation.rest.exception.ErrorResponse
import com.montebruni.holder.account.presentation.rest.request.CompleteCustomerRegistrationRequest
import com.montebruni.holder.account.presentation.rest.request.toCompleteCustomerRegistrationInput
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/customers")
class CustomerController(
    private val completeCustomerRegistration: CompleteCustomerRegistration
) {

    @Operation(
        summary = "Complete customer registration",
        description = "Complete the registration of a customer.",
        tags = ["Customer"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Customer registration completed",
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/complete-registration")
    fun completeRegistration(@RequestBody request: CompleteCustomerRegistrationRequest) {
        request
            .toCompleteCustomerRegistrationInput()
            .let(completeCustomerRegistration::execute)
    }
}
