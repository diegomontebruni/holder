package com.montebruni.holder.account.infrastructure.database.postgres

import com.montebruni.holder.account.infrastructure.database.postgres.model.RolePostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RolePostgresRepository : JpaRepository<RolePostgresModel, Long>
