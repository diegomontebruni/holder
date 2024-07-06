package com.montebruni.holder.stock.application.event.events

import com.montebruni.holder.stock.domain.event.EventData

interface Event {
    val data: EventData
}
