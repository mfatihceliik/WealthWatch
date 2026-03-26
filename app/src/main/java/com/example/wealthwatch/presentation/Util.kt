package com.example.wealthwatch.presentation

fun log(tag: String, vararg message: Any?) {
    val formattedMessage = message.joinToString(" || ") { it.toString() }
    android.util.Log.i(tag, formattedMessage)
}
fun formatVolume(volume: String): String {
    val vol = volume.toDoubleOrNull() ?: 0.0
    return when {
        vol >= 1_000_000_000 -> String.format("%.2fB", vol / 1_000_000_000)
        vol >= 1_000_000 -> String.format("%.2fM", vol / 1_000_000)
        else -> String.format("%.2f", vol)
    }
}