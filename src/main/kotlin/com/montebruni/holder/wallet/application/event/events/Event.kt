package com.montebruni.holder.wallet.application.event.events

import com.montebruni.holder.wallet.domain.event.EventData

interface Event {

    val entity: Any
    fun getData(): EventData
}
