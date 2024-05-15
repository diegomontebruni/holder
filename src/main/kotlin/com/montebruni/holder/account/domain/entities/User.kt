package com.montebruni.holder.account.domain.entities

import java.util.UUID

data class User(
    val id: UUID,
    val document: String,
)
