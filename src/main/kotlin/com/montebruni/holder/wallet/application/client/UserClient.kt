package com.montebruni.holder.wallet.application.client

import com.montebruni.holder.wallet.application.client.response.UserResponse
import java.util.UUID

interface UserClient {

    fun findById(id: UUID): UserResponse?
}
