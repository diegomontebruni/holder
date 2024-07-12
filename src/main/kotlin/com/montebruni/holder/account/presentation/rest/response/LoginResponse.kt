package com.montebruni.holder.account.presentation.rest.response

import com.montebruni.holder.account.application.usecase.output.LoginOutput
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Login response")
data class LoginResponse(
    @Schema(
        description = "Access token",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huLmRvZUBq" +
            "b2huZG9lLmNvbSIsImlhdCI6MTYxNjIzOTAyMn0.7J1Gzv"
    )
    val accessToken: String,
    @Schema(description = "Token expiration time", example = "1616239022")
    val expiresAt: Long
) {
    companion object
}

fun LoginResponse.Companion.from(output: LoginOutput) = LoginResponse(
    accessToken = output.accessToken,
    expiresAt = output.expiresAt
)
