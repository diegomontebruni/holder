package com.montebruni.holder.asset.application.event.events

import com.montebruni.holder.asset.domain.event.EventData

interface Event {
    val data: EventData
}
