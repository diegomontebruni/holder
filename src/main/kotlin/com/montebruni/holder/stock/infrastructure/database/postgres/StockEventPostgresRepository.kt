package com.montebruni.holder.stock.infrastructure.database.postgres

import com.montebruni.holder.stock.infrastructure.database.postgres.model.StockEventPostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StockEventPostgresRepository : JpaRepository<StockEventPostgresModel, UUID>
