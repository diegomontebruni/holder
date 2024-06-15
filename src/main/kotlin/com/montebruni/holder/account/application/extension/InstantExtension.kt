package com.montebruni.holder.account.application.extension

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Instant.format(pattern: String): String =
    DateTimeFormatter.ofPattern(pattern).let {
        this.atZone(ZoneId.systemDefault()).format(it)
    }

fun Instant.getFormattedDate(): String = this.format("dd/MM/yyyy")
fun Instant.getFormattedTime(): String = this.format("HH:mm")
