package com.montebruni.holder.account.infrastructure.database.postgres.repository

import com.montebruni.holder.account.infrastructure.database.postgres.model.UserPostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserPostgresRepository : JpaRepository<UserPostgresModel, UUID> {

    fun findByUsername(username: String): UserPostgresModel?
}
