package com.montebruni.holder.stock.infrastructure.database.postgres

import com.montebruni.holder.stock.infrastructure.database.postgres.model.StockPostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface StockPostgresRepository : JpaRepository<StockPostgresModel, UUID> {

    fun findBySymbol(symbol: String): StockPostgresModel?
}
