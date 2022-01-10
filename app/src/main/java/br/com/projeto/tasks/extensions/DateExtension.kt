package br.com.projeto.tasks.extensions

import java.text.SimpleDateFormat
import java.util.*

const val DATE_PATTERN = "dd/MM/yyyy"

fun Date.format() : String {
    return SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        .format(this)
}