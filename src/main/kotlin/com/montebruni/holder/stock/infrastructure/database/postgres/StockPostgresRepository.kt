package com.montebruni.holder.stock.infrastructure.database.postgres

import com.montebruni.holder.stock.infrastructure.database.postgres.model.StockPostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StockPostgresRepository : JpaRepository<StockPostgresModel, String>
