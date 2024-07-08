package com.montebruni.holder.account.presentation.interfaces.response

import java.util.UUID

data class FindUserByIdResponse(
    val id: UUID,
    val username: String,
    val status: String
)
