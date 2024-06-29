package com.montebruni.holder.asset.application.usecase

import com.montebruni.holder.asset.application.usecase.request.UpdateStockAssetInput

interface UpdateStockAsset {

    fun execute(input: UpdateStockAssetInput)
}
