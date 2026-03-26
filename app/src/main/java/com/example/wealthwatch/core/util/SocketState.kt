package com.example.wealthwatch.core.util

sealed interface SocketState<out T> {
    object Connecting : SocketState<Nothing>
    data class Connected<T>(val data: T) : SocketState<T>
    data class Disconnected(val reason: String?) : SocketState<Nothing>
}