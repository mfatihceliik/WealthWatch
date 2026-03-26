package com.example.wealthwatch.core.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

inline fun <T, R> Flow<T>.toSocketState(
        crossinline mapper: suspend (T) -> R
): Flow<SocketState<R>> =
        flow {
            emit(SocketState.Connecting)
            collect { value -> emit(SocketState.Connected(mapper(value))) }
        }
                .catch { e -> emit(SocketState.Disconnected(e.message)) }
