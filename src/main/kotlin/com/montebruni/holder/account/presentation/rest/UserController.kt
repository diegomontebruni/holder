package com.montebruni.holder.account.presentation.rest

import com.montebruni.holder.account.application.usecase.ChangeUserPassword
import com.montebruni.holder.account.application.usecase.CreateUser
import com.montebruni.holder.account.presentation.rest.exception.ErrorResponse
import com.montebruni.holder.account.presentation.rest.request.ChangeUserPasswordRequest
import com.montebruni.holder.account.presentation.rest.request.CreateUserRequest
import com.montebruni.holder.account.presentation.rest.request.toCreateUserInput
import com.montebruni.holder.account.presentation.rest.request.toInput
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/users")
class UserController(
    private val createUser: CreateUser,
    private val changeUserPassword: ChangeUserPassword
) {

    @Operation(
        summary = "Create User",
        description = "Create a new user. ***The username must be a valid email***",
        tags = ["User"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "User created",
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest) {
        request
            .toCreateUserInput()
            .let(createUser::execute)
    }

    @Operation(
        summary = "Change User Password",
        description = "Change the user password given an old password and a new password.",
        tags = ["User"]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User password changed",
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
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/change-password")
    fun changePassword(@RequestBody request: ChangeUserPasswordRequest) {
        request
            .toInput()
            .let(changeUserPassword::execute)
    }
}
