package com.montebruni.holder.wallet.infrastructure.client

import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.wallet.application.client.UserClient
import com.montebruni.holder.wallet.application.client.response.UserResponse
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserClientAdapter(
    private val userRepository: UserRepository
) : UserClient {

    override fun findById(id: UUID) = userRepository.findById(id)?.let { UserResponse(it.id) }
}
