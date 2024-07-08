package com.montebruni.holder.wallet.infrastructure.client

import com.montebruni.holder.account.presentation.interfaces.UserInterface
import com.montebruni.holder.wallet.application.client.UserClient
import com.montebruni.holder.wallet.application.client.response.UserResponse
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserClientAdapter(
    private val userInterface: UserInterface
) : UserClient {

    override fun findById(id: UUID) = userInterface.findById(id)?.let { UserResponse(it.id) }
}
