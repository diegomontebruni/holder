package com.montebruni.holder.account.presentation.rest

import com.montebruni.holder.account.application.usecase.InitiatePasswordRecovery
import com.montebruni.holder.account.presentation.rest.exception.ErrorResponse
import com.montebruni.holder.account.presentation.rest.request.InitiatePasswordRecoverRequest
import com.montebruni.holder.account.presentation.rest.request.toInput
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
@RequestMapping("v1/auth")
class AuthenticationController(
    private val initiatePasswordRecovery: InitiatePasswordRecovery
) {

    @Operation(
        summary = "Initiate Password Recovery",
        description = "Initiate the password recover process. " +
            "The user will receive an email with a link to recover the password.",
        tags = ["Authentication"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Password recover initiated",
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
    @PostMapping("/initiate-password-recovery")
    fun initiatePasswordRecover(@RequestBody request: InitiatePasswordRecoverRequest) {
        request
            .toInput()
            .let(initiatePasswordRecovery::execute)
    }
}
