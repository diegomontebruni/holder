package com.montebruni.holder.asset.domain.repositories

import com.montebruni.holder.asset.domain.entity.Asset

interface AssetRepository {

    fun save(asset: Asset): Asset
}
