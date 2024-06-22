package com.montebruni.holder.asset.infrastructure.database

import com.montebruni.holder.asset.domain.entity.Asset
import com.montebruni.holder.asset.domain.repositories.AssetRepository
import com.montebruni.holder.asset.infrastructure.database.postgres.AssetPostgresRepository
import com.montebruni.holder.asset.infrastructure.database.postgres.model.AssetPostgresModel
import com.montebruni.holder.asset.infrastructure.database.postgres.model.fromAsset
import com.montebruni.holder.asset.infrastructure.database.postgres.model.toAsset
import org.springframework.stereotype.Component

@Component
class AssetRepositoryAdapter(
    private val repository: AssetPostgresRepository
) : AssetRepository {

    override fun save(asset: Asset): Asset =
        AssetPostgresModel
            .fromAsset(asset)
            .let(repository::save)
            .toAsset()
}
