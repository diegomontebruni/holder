package com.montebruni.holder.account.infrastructure.database.postgres.adapter

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.port.UserRepository
import com.montebruni.holder.account.infrastructure.database.postgres.model.toUser
import com.montebruni.holder.account.infrastructure.database.postgres.repository.UserPostgresRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserRepositoryAdapter(
    private val repository: UserPostgresRepository
) : UserRepository {

    override fun findById(id: UUID): User? = repository.findByIdOrNull(id)?.toUser()
}
