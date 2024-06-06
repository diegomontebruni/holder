package com.montebruni.holder.account.application.event.events

import com.montebruni.holder.account.domain.event.EventData

interface Event {
    val entity: Any
    fun getData(): EventData
}
