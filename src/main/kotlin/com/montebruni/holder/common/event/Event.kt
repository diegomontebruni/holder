package com.montebruni.holder.common.event

interface Event {
    val entity: Any
    fun getData(): EventData
}
