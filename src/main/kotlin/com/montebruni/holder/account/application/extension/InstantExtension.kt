package com.montebruni.holder.account.application.extension

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Instant.formatFromPattern(pattern: String): String =
    DateTimeFormatter.ofPattern(pattern).let {
        this.atZone(ZoneId.systemDefault()).format(it)
    }

fun Instant.getFormattedDate(): String = this.formatFromPattern("dd/MM/yyyy")
fun Instant.getFormattedTime(): String = this.formatFromPattern("HH:mm")
