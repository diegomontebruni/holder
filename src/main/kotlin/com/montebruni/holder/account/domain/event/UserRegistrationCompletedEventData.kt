package com.montebruni.holder.account.domain.event

import com.montebruni.holder.account.domain.entity.Status
import com.montebruni.holder.account.domain.valueobject.Email
import com.montebruni.holder.account.domain.valueobject.Username
import java.util.UUID

data class UserRegistrationCompletedEventData(
    val userId: UUID,
    val username: Username,
    val status: Status,
    val name: String,
    val email: Email
) : EventData
