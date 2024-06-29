package com.montebruni.holder.asset.domain.repositories

import com.montebruni.holder.asset.domain.entity.Asset
import java.util.UUID

interface AssetRepository {

    fun save(asset: Asset): Asset
    fun findByWalletIdAndTicker(walletId: UUID, ticker: String): Asset?
}
