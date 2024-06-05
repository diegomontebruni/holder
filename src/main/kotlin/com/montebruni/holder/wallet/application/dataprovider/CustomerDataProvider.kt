package com.montebruni.holder.wallet.application.dataprovider

import com.montebruni.holder.wallet.application.dataprovider.data.CustomerData
import java.util.UUID

interface CustomerDataProvider {

    fun findById(id: UUID): CustomerData?
}
