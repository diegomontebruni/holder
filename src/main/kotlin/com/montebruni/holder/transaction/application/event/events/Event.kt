package com.montebruni.holder.transaction.application.event.events

import com.montebruni.holder.transaction.domain.event.EventData

interface Event {
    val entity: Any
    fun getData(): EventData
}
