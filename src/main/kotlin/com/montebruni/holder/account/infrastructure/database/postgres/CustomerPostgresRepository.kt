package com.montebruni.holder.account.infrastructure.database.postgres

import com.montebruni.holder.account.infrastructure.database.postgres.model.CustomerPostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CustomerPostgresRepository : JpaRepository<CustomerPostgresModel, UUID> {

    fun findByUserId(userId: UUID): CustomerPostgresModel?
}
