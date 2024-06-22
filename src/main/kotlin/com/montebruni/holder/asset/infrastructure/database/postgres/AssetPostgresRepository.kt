package com.montebruni.holder.asset.infrastructure.database.postgres

import com.montebruni.holder.asset.infrastructure.database.postgres.model.AssetPostgresModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AssetPostgresRepository : JpaRepository<AssetPostgresModel, UUID>
