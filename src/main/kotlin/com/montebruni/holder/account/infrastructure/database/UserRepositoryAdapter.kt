package com.montebruni.holder.account.infrastructure.database

import com.montebruni.holder.account.domain.entity.User
import com.montebruni.holder.account.domain.repositories.UserRepository
import com.montebruni.holder.account.infrastructure.database.postgres.UserPostgresRepository
import com.montebruni.holder.account.infrastructure.database.postgres.model.UserPostgresModel
import com.montebruni.holder.account.infrastructure.database.postgres.model.toUser
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.let

@Component
class UserRepositoryAdapter(
    private val repository: UserPostgresRepository
) : UserRepository {

    override fun save(user: User): User = UserPostgresModel.fromUser(user).let(repository::save).toUser()

    override fun findById(id: UUID): User? = repository.findByIdOrNull(id)?.toUser()

    override fun findByUsername(username: String): User? = repository.findByUsername(username)?.toUser()
}
